package com.services.core;

import com.services.core.entity.Record;
import com.services.core.service.RecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRecord {

    @Resource
    private RecordService recordService;

    @Test
    public void test2Insert(){
        Record record = new Record();
        record.setGmtRecord(new Date());
        record.setGmtCreate(new Date());
        record.setAppName("unknown");
        record.setEnvironment("unknown");
        record.setHost("192.168.100.33");
        record.setTraceId("192168100033161821517576210001ed");
        record.setEntranceDesc("http://192.168.100.33:8001/regress/partner/%E5%90%89%E5%A7%86");
        record.setWrapperRecord("QzBCY29tLmFsaWJhYmEuanZtLnNhbmRib3gucmVwZWF0ZXIucGx1Z2luLmNvcmUud3JhcHBlci5SZWNvcmRXcmFwcGVymAl0aW1lc3RhbXAHYXBwTmFtZQtlbnZpcm9ubWVudARob3N0B3RyYWNlSWQMZW50cmFuY2VEZXNjEmVudHJhbmNlSW52b2NhdGlvbg5zdWJJbnZvY2F0aW9uc2BMAAABeMUkBmEHdW5rbm93bgd1bmtub3duDjE5Mi4xNjguMTAwLjMzMCAxOTIxNjgxMDAwMzMxNjE4MjE1MTc1NzYyMTAwMDFlZDA9aHR0cDovLzE5Mi4xNjguMTAwLjMzOjgwMDEvcmVncmVzcy9wYXJ0bmVyLyVFNSU5MCU4OSVFNSVBNyU4NkMwPWNvbS5hbGliYWJhLmp2bS5zYW5kYm94LnJlcGVhdGVyLnBsdWdpbi5kb21haW4uSHR0cEludm9jYXRpb26mCnJlcXVlc3RVUkwKcmVxdWVzdFVSSQRwb3J0Bm1ldGhvZAtjb250ZW50VHlwZQRib2R5BWFzeW5jCGludm9rZUlkCXByb2Nlc3NJZAd0cmFjZUlkBWluZGV4CGVudHJhbmNlEXJlcXVlc3RTZXJpYWxpemVkEnJlc3BvbnNlU2VyaWFsaXplZBN0aHJvd2FibGVTZXJpYWxpemVkBXN0YXJ0A2VuZA5zZXJpYWxpemVUb2tlbgdoZWFkZXJzCXBhcmFtc01hcAR0eXBlCGlkZW50aXR5YTA9aHR0cDovLzE5Mi4xNjguMTAwLjMzOjgwMDEvcmVncmVzcy9wYXJ0bmVyLyVFNSU5MCU4OSVFNSVBNyU4NjAjL3JlZ3Jlc3MvcGFydG5lci8lRTUlOTAlODklRTUlQTclODbUH0EDR0VUTgBGy+nL6TAgMTkyMTY4MTAwMDMzMTYxODIxNTE3NTc2MjEwMDAxZWSRVDM8Y1FkYmIySnFaV04wU0Fkb1pXRmtaWEp6U0E5aFkyTmxjSFF0YkdGdVozVmhaMlVPZW1ndFEwNHNlbWc3Y1Qwd0xqa0VhRzl6ZEJNeE9USXVNVFk0TGpFd01DNHpNem80TURBeEdYVndaM0poWkdVdGFXNXpaV04xY21VdGNtVnhkV1Z6ZEhNQk1RcGpiMjV1WldOMGFXOXVCV05zYjNObERXTmhZMmhsTFdOdmJuUnliMndKYldGNExXRm5aVDB3RDJGalkyVndkQzFsYm1OdlpHbHVadzFuZW1sd0xDQmtaV1pzWVhSbENuVnpaWEl0WVdkbGJuUXdjMDF2ZW1sc2JHRXZOUzR3SUNoWGFXNWtiM2R6SUU1VUlERXdMakE3SUZkcGJqWTBPeUI0TmpRcElFRndjR3hsVjJWaVMybDBMelV6Tnk0ek5pQW9TMGhVVFV3c0lHeHBhMlVnUjJWamEyOHBJRU5vY205dFpTODRPUzR3TGpRek9Ea3VNVEUwSUZOaFptRnlhUzgxTXpjdU16WUdZV05qWlhCME1JZDBaWGgwTDJoMGJXd3NZWEJ3YkdsallYUnBiMjR2ZUdoMGJXd3JlRzFzTEdGd2NHeHBZMkYwYVc5dUwzaHRiRHR4UFRBdU9TeHBiV0ZuWlM5aGRtbG1MR2x0WVdkbEwzZGxZbkFzYVcxaFoyVXZZWEJ1Wnl3cUx5bzdjVDB3TGpnc1lYQndiR2xqWVhScGIyNHZjMmxuYm1Wa0xXVjRZMmhoYm1kbE8zWTlZak03Y1Qwd0xqbGFDWEJoY21GdGMwMWhjRWhhQm0xbGRHaHZaQU5IUlZRRWNHOXlkTlFmUVFweVpYRjFaWE4wVlZKTU1EMW9kSFJ3T2k4dk1Ua3lMakUyT0M0eE1EQXVNek02T0RBd01TOXlaV2R5WlhOekwzQmhjblJ1WlhJdkpVVTFKVGt3SlRnNUpVVTFKVUUzSlRnMkNuSmxjWFZsYzNSVlVra3dJeTl5WldkeVpYTnpMM0JoY25SdVpYSXZKVVUxSlRrd0pUZzVKVVUxSlVFM0pUZzJCR0p2WkhrQUMyTnZiblJsYm5SVWVYQmxUbG89MDxFT1dRaWVXbmh1YUlrT1dLbitXTXVlbUZqZVdJc09Xd2orUzhtZVM4dEZ2cG42bm1vb1htb29WZElRPT1OTAAAAXjFJAZhTAAAAXjFJAb8MDZvcmcuc3ByaW5nZnJhbWV3b3JrLmJvb3QubG9hZGVyLkxhdW5jaGVkVVJMQ2xhc3NMb2FkZXJID2FjY2VwdC1sYW5ndWFnZQ56aC1DTix6aDtxPTAuOQRob3N0EzE5Mi4xNjguMTAwLjMzOjgwMDEZdXBncmFkZS1pbnNlY3VyZS1yZXF1ZXN0cwExCmNvbm5lY3Rpb24FY2xvc2UNY2FjaGUtY29udHJvbAltYXgtYWdlPTAPYWNjZXB0LWVuY29kaW5nDWd6aXAsIGRlZmxhdGUKdXNlci1hZ2VudDBzTW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzg5LjAuNDM4OS4xMTQgU2FmYXJpLzUzNy4zNgZhY2NlcHQwh3RleHQvaHRtbCxhcHBsaWNhdGlvbi94aHRtbCt4bWwsYXBwbGljYXRpb24veG1sO3E9MC45LGltYWdlL2F2aWYsaW1hZ2Uvd2VicCxpbWFnZS9hcG5nLCovKjtxPTAuOCxhcHBsaWNhdGlvbi9zaWduZWQtZXhjaGFuZ2U7dj1iMztxPTAuOVpIWkMwOWNvbS5hbGliYWJhLmp2bS5zYW5kYm94LnJlcGVhdGVyLnBsdWdpbi5kb21haW4uSW52b2tlVHlwZZEEbmFtZWIEaHR0cEMwN2NvbS5hbGliYWJhLmp2bS5zYW5kYm94LnJlcGVhdGVyLnBsdWdpbi5kb21haW4uSWRlbnRpdHmRA3VyaWMwK2h0dHA6Ly8vcmVncmVzcy9wYXJ0bmVyLyVFNSU5MCU4OSVFNSVBNyU4Ni95QzA5Y29tLmFsaWJhYmEuanZtLnNhbmRib3gucmVwZWF0ZXIucGx1Z2luLmRvbWFpbi5JbnZvY2F0aW9unQhpbnZva2VJZAlwcm9jZXNzSWQHdHJhY2VJZAVpbmRleAhlbnRyYW5jZRFyZXF1ZXN0U2VyaWFsaXplZBJyZXNwb25zZVNlcmlhbGl6ZWQTdGhyb3dhYmxlU2VyaWFsaXplZAVzdGFydANlbmQOc2VyaWFsaXplVG9rZW4EdHlwZQhpZGVudGl0eWTL6svqMCAxOTIxNjgxMDAwMzMxNjE4MjE1MTc1NzYyMTAwMDFlZJFGGGNRZGJiMkpxWldOMEF1V1FpZVduaGc9PTCUUXpBOVkyOXRMbUZzYVdKaFltRXVhblp0TG5OaGJtUmliM2d1Y21Wd1pXRjBaWEl1Y0d4MVoybHVMbVJ2YldGcGJpNVNaWEJsWVhSbGNsSmxjM1ZzZEpNSGMzVmpZMlZ6Y3dkdFpYTnpZV2RsQkdSaGRHRmdWQVRwaFkzbHI3bm1pSkRsaXA4RDZaK3A1cUtGNXFLRk5MAAABeMUkBrdMAAABeMUkBucwNm9yZy5zcHJpbmdmcmFtZXdvcmsuYm9vdC5sb2FkZXIuTGF1bmNoZWRVUkxDbGFzc0xvYWRlcmIEamF2YWMwo2phdmE6Ly9jb20uYWxpYmFiYS5yZXBlYXRlci5jb25zb2xlLnNlcnZpY2UuaW1wbC5SZWdyZXNzU2VydmljZUltcGwvZmluZFBhcnRuZXJ+KExqYXZhL2xhbmcvU3RyaW5nOylMY29tL2FsaWJhYmEvanZtL3NhbmRib3gvcmVwZWF0ZXIvcGx1Z2luL2RvbWFpbi9SZXBlYXRlclJlc3VsdDs=");
        record.setRequest("[ {\n" +
                "  \"body\" : \"\",\n" +
                "  \"contentType\" : null,\n" +
                "  \"headers\" : {\n" +
                "    \"accept\" : \"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\",\n" +
                "    \"accept-encoding\" : \"gzip, deflate\",\n" +
                "    \"accept-language\" : \"zh-CN,zh;q=0.9\",\n" +
                "    \"cache-control\" : \"max-age=0\",\n" +
                "    \"connection\" : \"close\",\n" +
                "    \"host\" : \"192.168.100.33:8001\",\n" +
                "    \"upgrade-insecure-requests\" : \"1\",\n" +
                "    \"user-agent\" : \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36\"\n" +
                "  },\n" +
                "  \"method\" : \"GET\",\n" +
                "  \"paramsMap\" : { },\n" +
                "  \"port\" : 8001,\n" +
                "  \"requestURI\" : \"/regress/partner/%E5%90%89%E5%A7%86\",\n" +
                "  \"requestURL\" : \"http://192.168.100.33:8001/regress/partner/%E5%90%89%E5%A7%86\"\n" +
                "} ]");
        record.setResponse("吉姆成功匹配到小伙伴[韩梅梅]!");
        recordService.saveOrUpdate(record);
    }
}
