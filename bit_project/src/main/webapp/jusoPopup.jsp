<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<% 
   request.setCharacterEncoding("UTF-8");  //한글깨지면 주석제거
   //request.setCharacterEncoding("EUC-KR");  //해당시스템의 인코딩타입이 EUC-KR일경우에
   String inputYn = request.getParameter("inputYn"); 
   String roadFullAddr = request.getParameter("roadFullAddr"); 
   String roadAddrPart1 = request.getParameter("roadAddrPart1");
   String roadAddrPart2 = request.getParameter("roadAddrPart2");
   String addrDetail = request.getParameter("addrDetail");
   String engAddr = request.getParameter("engAddr");
   String jibunAddr = request.getParameter("jibunAddr");
   String zipNo = request.getParameter("zipNo");
   String admCd = request.getParameter("admCd");
   String rnMgtSn = request.getParameter("rnMgtSn");
   String bdMgtSn = request.getParameter("bdMgtSn");

%>
</head>
<script language="javascript">
// opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다. ("주소입력화면 소스"도 동일하게 적용시켜야 합니다.)
//document.domain = "abc.go.kr";

function init(){
		var dchk = 0;
		var filter = "win16|win32|win64|mac|macintel";
		var url = location.href;
		var confmKey = "";
		if ( navigator.platform ) {
			if ( filter.indexOf( navigator.platform.toLowerCase() ) < 0 ) {
				//모바일
				dchk = 2;
				confmKey = "devU01TX0FVVEgyMDIwMDIyMDE1NDEyNzEwOTQ4MzY=";
			}
			else {
				//피시
				dchk = 1;
				confmKey = "devU01TX0FVVEgyMDIwMDIyMDE1NDAxNzEwOTQ4MzU=";
			}
		}
   
   var resultType = "4"; // 도로명주소 검색결과 화면 출력내용, 1 : 도로명, 2 : 도로명+지번, 3 : 도로명+상세건물명, 4 : 도로명+지번+상세건물명
   var inputYn= "<%=inputYn%>";
   if(inputYn != "Y"){
      document.form.confmKey.value = confmKey;
      document.form.returnUrl.value = url;
      document.form.resultType.value = resultType;
      if(dchk==1){
    	  document.form.action="http://www.juso.go.kr/addrlink/addrLinkUrl.do"; //인터넷망  
      }else {
    	  document.form.action="http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do"; //모바일 웹인 경우, 인터넷망  
      }
      document.form.submit();
   }else{
      opener.jusoCallBack("<%=roadFullAddr%>", "<%=roadAddrPart1%>", "<%=addrDetail%>", "<%=roadAddrPart2%>",
            "<%=engAddr%>","<%=jibunAddr%>","<%=zipNo%>","<%=admCd%>","<%=rnMgtSn%>","<%=bdMgtSn%>");
      window.close();
      }
}
</script>
<body onload="init();">
   <form id="form" name="form" method="post">
      <input type="hidden" id="confmKey" name="confmKey" value=""/>
      <input type="hidden" id="returnUrl" name="returnUrl" value=""/>
      <input type="hidden" id="resultType" name="resultType" value=""/>
      <!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 START-->
      <!-- 
      <input type="hidden" id="encodingType" name="encodingType" value="EUC-KR"/>
       -->
      <!-- 해당시스템의 인코딩타입이 EUC-KR일경우에만 추가 END-->
</form>
</body>

</html>