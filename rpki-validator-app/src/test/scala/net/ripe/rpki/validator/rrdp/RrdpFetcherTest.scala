/**
 * The BSD License
 *
 * Copyright (c) 2010-2012 RIPE NCC
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the RIPE NCC nor the names of its contributors may be
 *     used to endorse or promote products derived from this software without
 *     specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.ripe.rpki.validator.rrdp

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import net.ripe.rpki.validator.support.ValidatorTestCase
import java.net.URI
import java.math.BigInteger

@RunWith(classOf[JUnitRunner])
class RrdpFetcherTest extends ValidatorTestCase {

  test("Should parse notification file") {

    val notificationXml = <notification xmlns="http://www.ripe.net/rpki/rrdp" version="1" session_id="c5a0c560-5f11-4507-9591-a00529dcb768" serial="4">
                            <snapshot serial="4" uri="http://localhost:8080/rpki-ca/rrdp/6BFED7D18B6C7937CCB860F756362507CE02AA0C7E65288F47B943EC5703D3E1.xml" hash="6BFED7D18B6C7937CCB860F756362507CE02AA0C7E65288F47B943EC5703D3E1"/>
                            <delta from="3" to="4" uri="http://localhost:8080/rpki-ca/rrdp/D8D60AAA846E7D977152AD6D09DC90A2CA80659D8FE22C70F9A8AB94899310B3.xml" hash="D8D60AAA846E7D977152AD6D09DC90A2CA80659D8FE22C70F9A8AB94899310B3"/>
                            <delta from="2" to="3" uri="http://localhost:8080/rpki-ca/rrdp/CBE8D991731F5183C35D47EF3E641D80E8154E4745CF04E2173C7E2F6BC91C2B.xml" hash="CBE8D991731F5183C35D47EF3E641D80E8154E4745CF04E2173C7E2F6BC91C2B"/>
                            <delta from="1" to="2" uri="http://localhost:8080/rpki-ca/rrdp/5D77E78CE2FA1B7BA24805A74D5F234CE217CC566AD3ADFB697978BA587C2DA2.xml" hash="5D77E78CE2FA1B7BA24805A74D5F234CE217CC566AD3ADFB697978BA587C2DA2"/>
                            <delta from="0" to="1" uri="http://localhost:8080/rpki-ca/rrdp/4D5A65B29D4C74D7C1A822160E0731E60DD522D9610D75293E1065976752FE0E.xml" hash="4D5A65B29D4C74D7C1A822160E0731E60DD522D9610D75293E1065976752FE0E"/>
                          </notification>

    val notification = Notification.fromXml(notificationXml)

    notification.serial should equal(BigInteger.valueOf(4))
  }

  test("Should parse snapshot") {

    val snapshotXml = <snapshot xmlns="http://www.ripe.net/rpki/rrdp" version="1" session_id="c5a0c560-5f11-4507-9591-a00529dcb768" serial="4">
                        <publish uri="rsync://localhost:10873/repo/5584b9c4b01956ab395f4ea46ea53fd19b783eb5.cer">
                          MIIE7TCCA9WgAwIBAgIBAjANBgkqhkiG9w0BAQsFADANMQswCQYDVQQDEwJUQTAeFw0xNDEwMDkx MjUzMTNaFw0xNTEwMDkxMjUzMTNaMDMxMTAvBgNVBAMTKDU1ODRiOWM0YjAxOTU2YWIzOTVmNGVh NDZlYTUzZmQxOWI3ODNlYjUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCABzwYRcpX i7SpF8RJV5zAWlOlTNjqfV7jEhM7cyN8bHHqbtUm3O8/KOVPMbNssV3atP1r0gF39fAv3JFsS0gb UNqaJEjC1Jpx8kPPJJvH33TW7qvLsyVVA5MVngCH/aW0zn/27RrX9Ox0bHwi69kLpT687DMEaemo rc+ZDYvupVWQHYhewqhdD1HXQaxxZCtpene/X4q9hUiw4a2v7mvrhPt60WaT9k60qkmBH1k91Yj/ oxOKUveRQMbluWJNzebi2OHd6IFjX7+nTjaBsSgRq0/O8Md8XAhHuv07bn0dVm5mb7W0SkfYbgkl lgLH+D7M6vJ3dYmvmQzSNTMO7cCVAgMBAAGjggIwMIICLDAdBgNVHQ4EFgQUVYS5xLAZVqs5X06k bqU/0Zt4PrUwHwYDVR0jBBgwFoAUi4sKS+17VVjgogIK+OssESC+INMwDwYDVR0TAQH/BAUwAwEB /zAOBgNVHQ8BAf8EBAMCAQYwQwYIKwYBBQUHAQEENzA1MDMGCCsGAQUFBzAChidodHRwOi8vbG9j YWxob3N0OjgwODAvcnBraS1jYS90YS90YS5jZXIwge0GCCsGAQUFBwELBIHgMIHdMFYGCCsGAQUF BzAFhkpyc3luYzovL2xvY2FsaG9zdDoxMDg3My9yZXBvLzNhODdhNGIxLTZlMjItNGE2My1hZDBm LTA2ZjgzYWQzY2ExNi9kZWZhdWx0LzCBggYIKwYBBQUHMAqGdnJzeW5jOi8vbG9jYWxob3N0OjEw ODczL3JlcG8vM2E4N2E0YjEtNmUyMi00YTYzLWFkMGYtMDZmODNhZDNjYTE2L2RlZmF1bHQvNTU4 NGI5YzRiMDE5NTZhYjM5NWY0ZWE0NmVhNTNmZDE5Yjc4M2ViNS5tZnQwWgYDVR0fBFMwUTBPoE2g S4ZJcnN5bmM6Ly9sb2NhbGhvc3Q6MTA4NzMvcmVwby84YjhiMGE0YmVkN2I1NTU4ZTBhMjAyMGFm OGViMmMxMTIwYmUyMGQzLmNybDAYBgNVHSABAf8EDjAMMAoGCCsGAQUFBw4CMB4GCCsGAQUFBwEH AQH/BA8wDTALBAIAATAFAwMAwKgwDQYJKoZIhvcNAQELBQADggEBADq6JEc6QzdMQTfyphCZeHHJ fTb0Q2Uoe543hOT2biptak1TtnMfttLg03dqS0QHJ9pZMW+V7BJhf1LeV1z16W+tZgXf2Uwoiaa+ 3YWf+4sqqDP8SS5ivl1LCBg++Wc5QPBgUEofbiRSTkLTkFxAWmLNRJdz7dQz+164po1sg9nfJsNA E0aI0ZCdLPqY5p1diHyh/FA1Aw2uo2QxYot9sQuAWxSP96bBazYOxGZso0pL0883Cd5G4hR3uxJX L7ig/PRKoNsBP6G4zKbcfi7EEql2V0quvbXTYhQr4f7qDhSpXAKMYaVjCmR0EjcgLh/neHiYX7UT S0WpN+94mIHF+CQ=
                        </publish>
                        <publish uri="rsync://localhost:10873/repo/8b8b0a4bed7b5558e0a2020af8eb2c1120be20d3.mft">
                          MIAGCSqGSIb3DQEHAqCAMIACAQMxDzANBglghkgBZQMEAgEFADCABgsqhkiG9w0BCRABGqCAJIAE gdwwgdkCAQIYDzIwMTQxMDA5MTMwMzE0WhgPMjAxNDEwMTAxMzAzMTRaBglghkgBZQMEAgEwgaYw URYsNTU4NGI5YzRiMDE5NTZhYjM5NWY0ZWE0NmVhNTNmZDE5Yjc4M2ViNS5jZXIDIQB7zO2E6nqI OSRo7lwUKQQWMhA9gYQIhAHowixipH5WRDBRFiw4YjhiMGE0YmVkN2I1NTU4ZTBhMjAyMGFmOGVi MmMxMTIwYmUyMGQzLmNybAMhAKGKbZc1yDLXzT3JV1UjtyFPXK0+f+iplOwyCA45XQWOAAAAAAAA oIAwggRtMIIDVaADAgECAgEEMA0GCSqGSIb3DQEBCwUAMA0xCzAJBgNVBAMTAlRBMB4XDTE0MTAw OTEzMDMxNFoXDTE0MTAxNjEzMDMxNFowMzExMC8GA1UEAxMoY2EwNWQzYmU5MTE4ZWFlNTExZDMy ODg4Mzc5MTM2ZDhkYzAxNjI0MjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAI7au6nE qjpV/qunxtKzkfp+R8akHUVhfo4wJ4eb2qn1sIX4suY96209RZjrccbujnkuLSuXVq5dbgFb3WwO 6pk112eH1tnJSV9G+ZQ5xc1wg7bENmFi3slNvX7+0jPVFxdqeajfK+2ZfXReKmgUyBn0lt55HZ3F zIno8V/tiZxKZRKxqETCQStYz1ECl/kydV98XRhrmLm+AsV7H5GqjXZe+IncByUVZT3zqBj7sSFC N6Hz1JMqjhkJcdXOudtwrjOBpGVC1wCEBZ9In+G0s4xgeeMSozOs3rGCeRu+hK8qsKM39LZNzxAQ 7GxkMSIOszU4t66v7D+LvEYOqxYq8wECAwEAAaOCAbAwggGsMB0GA1UdDgQWBBTKBdO+kRjq5RHT KIg3kTbY3AFiQjAfBgNVHSMEGDAWgBSLiwpL7XtVWOCiAgr46ywRIL4g0zAOBgNVHQ8BAf8EBAMC B4AwQwYIKwYBBQUHAQEENzA1MDMGCCsGAQUFBzAChidodHRwOi8vbG9jYWxob3N0OjgwODAvcnBr aS1jYS90YS90YS5jZXIwZQYIKwYBBQUHAQsEWTBXMFUGCCsGAQUFBzALhklyc3luYzovL2xvY2Fs aG9zdDoxMDg3My9yZXBvLzhiOGIwYTRiZWQ3YjU1NThlMGEyMDIwYWY4ZWIyYzExMjBiZTIwZDMu bWZ0MFoGA1UdHwRTMFEwT6BNoEuGSXJzeW5jOi8vbG9jYWxob3N0OjEwODczL3JlcG8vOGI4YjBh NGJlZDdiNTU1OGUwYTIwMjBhZjhlYjJjMTEyMGJlMjBkMy5jcmwwGAYDVR0gAQH/BA4wDDAKBggr BgEFBQcOAjAhBggrBgEFBQcBBwEB/wQSMBAwBgQCAAEFADAGBAIAAgUAMBUGCCsGAQUFBwEIAQH/ BAYwBKACBQAwDQYJKoZIhvcNAQELBQADggEBAHWkU864MAh5reveXpJq4P/bBSQvUYv9Gg8wlOKm Y0zCoTE9ijXdzAMxfd2NMlztdgAFEEPnJ4INmbVme5p1iWz1bHrkNvQxU7LOTkXyt/Ze/BuTnNYX 8qJC7NlIbm7D/c3OhQRl8QIJ5FuWWhAJDpldNBYWcdZR4I/sTmPgdq8rUivYn+RWbEdtF4nx5B3G /uADvDhXWac91Pd9J4KkgRFnrMOfJFCYBPBYkjqtYamiVGuw1deOiKmYYegJXceJFE1roY8zd8EW rGlDh58opG+N5gRI+nKX6ZDx+KXXs4hsGwgse4D0ASuQCAI2qQPSKQlVoGj8kGmHkTjoK0aNC7AA ADGCAawwggGoAgEDgBTKBdO+kRjq5RHTKIg3kTbY3AFiQjANBglghkgBZQMEAgEFAKBrMBoGCSqG SIb3DQEJAzENBgsqhkiG9w0BCRABGjAcBgkqhkiG9w0BCQUxDxcNMTQxMDA5MTMwMzE0WjAvBgkq hkiG9w0BCQQxIgQg6QKgjeeELqGnLkRNanuliTO6wtGVymZzvf5lFhXXGX0wDQYJKoZIhvcNAQEB BQAEggEAM559NgdbzeEMWLlJMMrnn+lgn1QAtZEfllsQQW/1yN0UpZyb0wg651aAD8Kn0KjBjbMv pe13ayTdFV/qGaIwDrRaUSvZ8q/tWxVvwwQA0kD6Qob3K9kBmqM/48EaRJEySz2Fd9mcEvt87ekf r8JiN+zWPvJwcqHO+ySXDvjZpFlZBbhttjhI5KkKllE6XPVhoK2cF6Ydvv3eg+SWyfsrkB+18B/u D2eXWgWPWruZDjzZm+pryov01upqJI+jN5gR4YWYi2ZaLukXUvL3tNDh7d4mwXs12jEu56PiY1ZI mD5G0aBhUnhHONnOqQAFGfWnOzwA+j0cCLNScww94WvDKwAAAAAAAA==
                        </publish>
                        <publish uri="rsync://localhost:10873/repo/8b8b0a4bed7b5558e0a2020af8eb2c1120be20d3.crl">
                          MIIBnTCBhgIBATANBgkqhkiG9w0BAQsFADANMQswCQYDVQQDEwJUQRcNMTQxMDA5MTMwMzE0WhcN MTQxMDEwMTMwMzE0WjAUMBICAQMXDTE0MTAwOTEzMDMxNFqgLzAtMB8GA1UdIwQYMBaAFIuLCkvt e1VY4KICCvjrLBEgviDTMAoGA1UdFAQDAgECMA0GCSqGSIb3DQEBCwUAA4IBAQCPYoTWjt02G4WI kPRpXDIhF7UK9xXuKfnx1bIsX9+5no/gpI51Plc49gpLMa7thgifPkmgdbqos5mesPlm7sTRZ2K3 wmn41FAEwSMbtnV9/XURVo2iYRyzU6W+/Fehfxaj7+UYbYXBzZ6akjFAEiRsX4Cl6DmTDnBX4jsT yghR3+MD4eyFu3vs5VZoX+hJFtahE/WIVcuZO+LNRJXJWUywV+iNlgSe/5q9cW8MecxaKUfByGrk teYhUWKmPDehjt9I+fJ17JiQeW1wJnM8jVxOq+mZ+Nvffj6L7EVa4MTx7MP7EPcBsS5FGOsqjbjI VfxRCTFZ8U6TyaQj/wJB2yPu
                        </publish>
                        <publish uri="rsync://localhost:10873/repo/3a87a4b1-6e22-4a63-ad0f-06f83ad3ca16/default/5584b9c4b01956ab395f4ea46ea53fd19b783eb5.mft">
                          MIAGCSqGSIb3DQEHAqCAMIACAQMxDzANBglghkgBZQMEAgEFADCABgsqhkiG9w0BCRABGqCAJIAE gYgwgYUCAQIYDzIwMTQxMDA5MTMwMzE1WhgPMjAxNDEwMTAxMzAzMTVaBglghkgBZQMEAgEwUzBR Fiw1NTg0YjljNGIwMTk1NmFiMzk1ZjRlYTQ2ZWE1M2ZkMTliNzgzZWI1LmNybAMhAD7fZly4/fhd VjW1Ao1onqc2vZs806Kx1GU53Iu2Hb0eAAAAAAAAoIAwggUWMIID/qADAgECAgECMA0GCSqGSIb3 DQEBCwUAMDMxMTAvBgNVBAMTKDU1ODRiOWM0YjAxOTU2YWIzOTVmNGVhNDZlYTUzZmQxOWI3ODNl YjUwHhcNMTQxMDA5MTMwMzE1WhcNMTQxMDE2MTMwMzE1WjAzMTEwLwYDVQQDEyhlMDY4MjNiMmUx MzliZDhjZGYxZGIwOTkzYTFiZjdkZWNhOTc4MTM4MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB CgKCAQEAtOwjDAWUYuybCpz86ooG2uwQe/JDyDLUZULp5DEi3hJ+M1QXvvl7fpZJODD0e/TKHjQt Tp0A7hvB1Me5Q3R1Qb1fa/Fue6h5eC/pEWT1SlASSMKlzx6eroays8hCjfmCqDjSXdtLmIyzEiJX 8hYyLBNA0q/0Ho+7K8Hjr8xwJMEsIFdqWIKcacwqtSc0ojkewQ9ceQ6nfJAfK7G05scgJyx8fGyC x88vHvYBtuPFGlp9XNqKUyK9GfACJvfwLMDltzedbn2+9lbfp7MyhVNjCjamt1b/TW932ENLqpSp NRhwCJRlPEEw33LyHLZJv5wAuCpZTy6RaR7EToM+yJnHgwIDAQABo4ICMzCCAi8wHQYDVR0OBBYE FOBoI7LhOb2M3x2wmTob997Kl4E4MB8GA1UdIwQYMBaAFFWEucSwGVarOV9OpG6lP9GbeD61MA4G A1UdDwEB/wQEAwIHgDBmBggrBgEFBQcBAQRaMFgwVgYIKwYBBQUHMAKGSnJzeW5jOi8vbG9jYWxo b3N0OjEwODczL3JlcG8vM2E4N2E0YjEtNmUyMi00YTYzLWFkMGYtMDZmODNhZDNjYTE2L2RlZmF1 bHQvMIGVBggrBgEFBQcBCwSBiDCBhTCBggYIKwYBBQUHMAuGdnJzeW5jOi8vbG9jYWxob3N0OjEw ODczL3JlcG8vM2E4N2E0YjEtNmUyMi00YTYzLWFkMGYtMDZmODNhZDNjYTE2L2RlZmF1bHQvNTU4 NGI5YzRiMDE5NTZhYjM5NWY0ZWE0NmVhNTNmZDE5Yjc4M2ViNS5tZnQwgYgGA1UdHwSBgDB+MHyg eqB4hnZyc3luYzovL2xvY2FsaG9zdDoxMDg3My9yZXBvLzNhODdhNGIxLTZlMjItNGE2My1hZDBm LTA2ZjgzYWQzY2ExNi9kZWZhdWx0LzU1ODRiOWM0YjAxOTU2YWIzOTVmNGVhNDZlYTUzZmQxOWI3 ODNlYjUuY3JsMBgGA1UdIAEB/wQOMAwwCgYIKwYBBQUHDgIwIQYIKwYBBQUHAQcBAf8EEjAQMAYE AgABBQAwBgQCAAIFADAVBggrBgEFBQcBCAEB/wQGMASgAgUAMA0GCSqGSIb3DQEBCwUAA4IBAQBk 2oXljr03wegjyGX8ZPP9CuomGPX6p0DvsPoFNOJ66XrI+cYD1depyqTCrbj44zlctAZ+mlG+aM6i F80zTBdNVftHp8jytJAzSAeC8GYMcdxPUO0xYztLx9rcWPGOja59b8Uj24qFhrfRS88XCz4YmSvc Dvup6O9IJcJt7ddoIdTjCJoOM04rAbzZr+xdCRcW2Bq94xXLGaGV24B5Oibbxw54m+JMPxuzjPZG 5+yEBrjFfr0MS0HcSVva4Ifo7bJr2yGuxjMqC6xzLk3/9Hx0GSIv6C/Q5je0rTRWb4KqFja5JrdX vyqqO5YijS5IsDuehwaNk+iS/0e2FbHCQpI2AAAxggGsMIIBqAIBA4AU4GgjsuE5vYzfHbCZOhv3 3sqXgTgwDQYJYIZIAWUDBAIBBQCgazAaBgkqhkiG9w0BCQMxDQYLKoZIhvcNAQkQARowHAYJKoZI hvcNAQkFMQ8XDTE0MTAwOTEzMDMxNVowLwYJKoZIhvcNAQkEMSIEIDKifODP2ZOhdaw1sy0J0N5C 77N5b3Z3lmK7Ak8fzo47MA0GCSqGSIb3DQEBAQUABIIBAJ/apxowMYH0bNmPPOFGbikh4BaBDiVG 4PX/BuWmxvLPQh0foY2NJGVsLiskebzWBkHUGp9Juwe/FN381ynafmihnVIYqqmee8fCn3M6b8P5 gt9h+IoLgjDBh0Pi+6EIGRFz8GNM2T9LuIMdKYUntmDVu5HN83/DrENPgBHwMvooCk+dHtpwHiBt eWG49Y8zakfqmPR9TjbeydZ5Xr8zsOn0sAbh5zROm7hT9/7wvWApyWMt6e0g+N6EaLT0uJUeJIOz z4F2c0OQzf8tZuqa31zwBMIKV9yr3uwwe7O/4xhnKxFbck4bxNXZUs6cyvIzbM9Bzv8IKTZZV210 s1F0kdAAAAAAAAA=
                        </publish>
                        <publish uri="rsync://localhost:10873/repo/3a87a4b1-6e22-4a63-ad0f-06f83ad3ca16/default/5584b9c4b01956ab395f4ea46ea53fd19b783eb5.crl">
                          MIIBwzCBrAIBATANBgkqhkiG9w0BAQsFADAzMTEwLwYDVQQDEyg1NTg0YjljNGIwMTk1NmFiMzk1 ZjRlYTQ2ZWE1M2ZkMTliNzgzZWI1Fw0xNDEwMDkxMzAzMTRaFw0xNDEwMTAxMzAzMTRaMBQwEgIB ARcNMTQxMDA5MTMwMzE0WqAvMC0wHwYDVR0jBBgwFoAUVYS5xLAZVqs5X06kbqU/0Zt4PrUwCgYD VR0UBAMCAQIwDQYJKoZIhvcNAQELBQADggEBABAWm43xk6ZO7LA6wLfq0ISdDswC9tABAB8cf6X2 lj5HZTeIPN1kYkmQGQL92ti6taMWyv4WqrcwEudHRNAMYBw6FoCWnpKjo8fBpcmTEnFOoE4SmTYe sUjxFXHVTvSUyVVs3fWX/rATuXnwpGluJt5Lx90U24bed1aOmBRZkOJY+mnmaQ2yvRsDpT0nINs9 H3/zK0c1GafgLARZB9fn6lYEsofKO5jvysOpKBRgGAcsRJ6vaT0UDgUeoxtU7nA1V2TF7Czng3l8 1HKNP5mPRorwD9+9dWnqKygKO05RcLx+fNDAB6nS1Bm3ekRsvfD+rl/PqzpNsOfvycSkFAjYb2w=
                        </publish>
                      </snapshot>

    Snapshot.fromXml(snapshotXml)
  }

  test("Should parse delta") {
    val deltaXml = <deltas xmlns="http://www.ripe.net/rpki/rrdp" version="1" session_id="c5a0c560-5f11-4507-9591-a00529dcb768" from="1" to="2">
                     <delta serial="2">
                       <publish uri="rsync://localhost:10873/repo/3a87a4b1-6e22-4a63-ad0f-06f83ad3ca16/default/5584b9c4b01956ab395f4ea46ea53fd19b783eb5.mft">
                         MIAGCSqGSIb3DQEHAqCAMIACAQMxDzANBglghkgBZQMEAgEFADCABgsqhkiG9w0BCRABGqCAJIAE gYgwgYUCAQEYDzIwMTQxMDA5MTI1MzE1WhgPMjAxNDEwMTAxMjUzMTVaBglghkgBZQMEAgEwUzBR Fiw1NTg0YjljNGIwMTk1NmFiMzk1ZjRlYTQ2ZWE1M2ZkMTliNzgzZWI1LmNybAMhALLn6G0siqbC LoGAA4kjHTBRrDk/7cXVumKWcrki/MIkAAAAAAAAoIAwggUWMIID/qADAgECAgEBMA0GCSqGSIb3 DQEBCwUAMDMxMTAvBgNVBAMTKDU1ODRiOWM0YjAxOTU2YWIzOTVmNGVhNDZlYTUzZmQxOWI3ODNl YjUwHhcNMTQxMDA5MTI1MzE1WhcNMTQxMDE2MTI1MzE1WjAzMTEwLwYDVQQDEyhhNDAzNDZhODk5 ZTQ4ZGNiMTBkYmViNDM1MTVhMTlkZjlmMGUyYWNiMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB CgKCAQEAryJsxwlV/I/yoOSiJzSDjXRglox/s0j1GnUhk0sF0EVHPOjM+GhSadroMPr0sMgnx8BP U3Hk0Krh+I4Taijxr3/aFXIUXi6e5PovttkpLtIi4V8arEZwxN0pnfm0wbJP9Hgi7CWRLs5XMOsz 7yBjsYSfulosiojeVrcZ7MKRCuUFp+m4aaIZFtib848u417TFHojpkSMg8RYNkjsVEIoGVP7dVbF OmTUo1HAMkl/PmLBmBz3Kn+8PrzaDsOj5o7ajetpFxqGFw1k5i8Xld1zO6ZBHOI3svUOMxMarcjX wpIFXhBj9clg6z0R12SJ6QUJSzBh3DXOKTf8xnMPgP2/JwIDAQABo4ICMzCCAi8wHQYDVR0OBBYE FKQDRqiZ5I3LENvrQ1FaGd+fDirLMB8GA1UdIwQYMBaAFFWEucSwGVarOV9OpG6lP9GbeD61MA4G A1UdDwEB/wQEAwIHgDBmBggrBgEFBQcBAQRaMFgwVgYIKwYBBQUHMAKGSnJzeW5jOi8vbG9jYWxo b3N0OjEwODczL3JlcG8vM2E4N2E0YjEtNmUyMi00YTYzLWFkMGYtMDZmODNhZDNjYTE2L2RlZmF1 bHQvMIGVBggrBgEFBQcBCwSBiDCBhTCBggYIKwYBBQUHMAuGdnJzeW5jOi8vbG9jYWxob3N0OjEw ODczL3JlcG8vM2E4N2E0YjEtNmUyMi00YTYzLWFkMGYtMDZmODNhZDNjYTE2L2RlZmF1bHQvNTU4 NGI5YzRiMDE5NTZhYjM5NWY0ZWE0NmVhNTNmZDE5Yjc4M2ViNS5tZnQwgYgGA1UdHwSBgDB+MHyg eqB4hnZyc3luYzovL2xvY2FsaG9zdDoxMDg3My9yZXBvLzNhODdhNGIxLTZlMjItNGE2My1hZDBm LTA2ZjgzYWQzY2ExNi9kZWZhdWx0LzU1ODRiOWM0YjAxOTU2YWIzOTVmNGVhNDZlYTUzZmQxOWI3 ODNlYjUuY3JsMBgGA1UdIAEB/wQOMAwwCgYIKwYBBQUHDgIwIQYIKwYBBQUHAQcBAf8EEjAQMAYE AgABBQAwBgQCAAIFADAVBggrBgEFBQcBCAEB/wQGMASgAgUAMA0GCSqGSIb3DQEBCwUAA4IBAQBW W3W9dPLf7kK1ZgmHg6rpTxiIAeA32MdH34uPzlJmmt+FK5m9DhwRzu0as2rVju/oDLmKrU0dbtrP N8/0iNP6HU9l/gcRXE0HWjMqay356ZmHw3OsZ0oYS7TL3Xy2akkpFLTvePxvte11OcotkVaLvFto +Xmn8OL2puIA0yxj+m/1d23K6isTLDHj9mgj9CtcKcTCvJxqSbQgL8G8zB0pbgUvMxlVYgeyDaQ3 x3GzlLlP6xsvul0s5S/CSJr43KlR+F5wPuCyOQtn/GOeqYB8RG39+TSoLIhtMHuGUtzHCldJ2j58 KJfDekN9ZwdAVr+Ov6cU/WvPSusE2CIs7FLtAAAxggGsMIIBqAIBA4AUpANGqJnkjcsQ2+tDUVoZ 358OKsswDQYJYIZIAWUDBAIBBQCgazAaBgkqhkiG9w0BCQMxDQYLKoZIhvcNAQkQARowHAYJKoZI hvcNAQkFMQ8XDTE0MTAwOTEyNTMxNVowLwYJKoZIhvcNAQkEMSIEIBIyZmQ2l9xHQclwe3yZ2n+G qJU1TLAEkZpUffgdhxheMA0GCSqGSIb3DQEBAQUABIIBACrPg43DXqQTyC4Z8uRwq+JVWPQdJrmJ l4p/3E9ppNfi4Sa5l36OKZwU8RTzsdudCJk3lvTwG17ej7L+iJLkj2i1tMZRIIH/ZAZtL++qXgZ7 NlZ8VtBFwhZTlQR9C67l+rbXq5sENcFokwgr17UFtuO8rWftfvBJjxlKaOBUXma4LfSrS2hmcJjl ioek8E3YJFmFFnTH4GJNKvzWbRWBvjeXRT06fJXCggaw4AhW2tBZuOibx3qqqtSAGGtgAT/r4oy2 8Afgggh7o/oW2xFgkU/PNp+SonTClN7YI4kEqHz4lC+jukWMtgNrvgcGFOK/y9W3FaQGWBkG7uZJ aEo2Hm8AAAAAAAA=
                       </publish>
                       <publish uri="rsync://localhost:10873/repo/3a87a4b1-6e22-4a63-ad0f-06f83ad3ca16/default/5584b9c4b01956ab395f4ea46ea53fd19b783eb5.crl">
                         MIIBrTCBlgIBATANBgkqhkiG9w0BAQsFADAzMTEwLwYDVQQDEyg1NTg0YjljNGIwMTk1NmFiMzk1 ZjRlYTQ2ZWE1M2ZkMTliNzgzZWI1Fw0xNDEwMDkxMjUzMTVaFw0xNDEwMTAxMjUzMTVaoC8wLTAf BgNVHSMEGDAWgBRVhLnEsBlWqzlfTqRupT/Rm3g+tTAKBgNVHRQEAwIBATANBgkqhkiG9w0BAQsF AAOCAQEAZb/HkxIKYqIZ+nXI2HsR/KfyKN3jTC4pKDPfPxDacH4gAeCPU/JGzh6DGEMfrL1i/cD6 RQfG9wDKxVQR0+s5OVejagVgMrEGL15wS1bwSxhp/2QMyOWu0ySVwEUWGvaXr68HS0iTmYXtb+1H BOnlbF7Q5etpEY1febCVz5drbqaF0QVtE6ibCuSEF3GwdzfTC1Jh3PJYue1+irNhv//VHA3n4INB wUyY6tn4LxB2jBxGYKzYHK/DDTEc2k+n5O2q4KHnH/0xOq9te8eSCb3J8d3JZVGMC/Zyrtm0Dy45 OTRVtMtdTdJ05NfkGMAiAvviRN7kEPbvxD58jKAUT/cb+w==
                       </publish>
                     </delta>
                   </deltas>

    Deltas.fromXml(deltaXml)
  }

//  Slow interop test, skipping for normal builds.. use this to parse the xml generate by Rob Austein
//  test("Should read notify file") {
//    //      val fetcher = RrdpFetcher.initialise(URI.create("http://localhost:8080/rpki-ca/notify/notify.xml"))
//    val fetcher = RrdpFetcher.initialise(URI.create("http://akkht.hactrn.net/rrdp/updates.xml"))
//
//    fetcher.update match {
//      case withUpdates: RrdpFetcherWithUpdates => {
//        println(withUpdates.updates)
//
//        withUpdates.fetcher.update match {
//          case noUpdates: RrdpFetcherWithoutUpdates => // ok
//          case _ => fail("Expected no more updates")
//        }
//      }
//      case _ => fail("expected fetcher with updates")
//    }
//  }

}