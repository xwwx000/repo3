//浏览器中Backspace不可用  
$(document).keydown(function(e){   
       var keyEvent;   
       if(e.keyCode==8){   
           var d=e.srcElement||e.target;   
            if(d.tagName.toUpperCase()=='INPUT'||d.tagName.toUpperCase()=='TEXTAREA'){   
                keyEvent=d.readOnly||d.disabled;   
            }else{   
                keyEvent=true;   
            }   
        }else{   
            keyEvent=false;   
        }   
        if(keyEvent){   
            e.preventDefault();   
        }   
});   
/**
 * 当前时间
 */
function currentDateTime(){
	var d = new Date();
	var vYear = d.getFullYear();
	var vMon = d.getMonth() + 1;
	var vDay = d.getDate();
	vMon = vMon>9 ? vMon:"0"+vMon;
	vDay = vDay>9 ? vDay:"0"+vDay;
	var hours = d.getHours();
	var minutes = d.getMinutes();
	var seconds = d.getSeconds();
	hours = hours>9 ? hours:"0"+hours;
	minutes = minutes>9 ? minutes:"0"+minutes;
	seconds = seconds>9 ? seconds:"0"+seconds;

	datetmp = vYear+"-"+vMon+"-"+vDay+" "+hours+":"+minutes+":"+seconds;

	return datetmp;
}
//对多位小数进行四舍五入
//num是要处理的数字  v为要保留的小数位数
function decimal(num,v){
	var vv = Math.pow(10,v);
	return Math.round(num*vv)/vv;
}

//根据年月取最后一天
function getLastDay(year,month){
	//构造一个日期对象：
	var day = new Date(parseInt(year),parseInt(month),0); 
	//获取天数：
	return day.getDate();
}