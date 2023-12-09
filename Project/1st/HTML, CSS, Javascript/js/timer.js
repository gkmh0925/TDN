let count = 165210;
let timerID = null;

onmessage = function(e){ //onmessage : 브라우저에서 작동되면
    if(e.data == "start"){
        if(timerID != null) 
        return;  //타이머가 작동하면 리턴(값을 저장하라)
        timerID = setInterval(myCallback, 1000); //1초마다 myCallback 호출
    }else if(e.data == "stop"){
        if(timerID == null)
        return; //스톱이면 null 상태로 초기화
        clearInterval(timerID);
        close();
    }
}

function myCallback(){
    count++;
    postMessage(count);
}