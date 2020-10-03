function MzCalendar(handleId)
{
  this.id = handleId;
  this.colors  =
  {
    "dark"   : "#0000D0",
    "word"   : "#000040",
    "light"  : "#FFFFFF",
    "today"     : "#FF9933",
    "weekend"   : "#FFA000",
    "wordDark"  : "#C0C0C0",
    "dayBgColor"  : "#F5F5FA",
    "borderDark"  : "#FFE4C4"
  };
  try{var NS = navigator.userAgent.indexOf("Netscape")>1;}catch(e){var NS=false;}
  this.chinese  = {"song" : (NS ? unescape("%CB%CE%CC%E5") : unescape("%u5B8B%u4F53"))};
  this.elements = {"days" : new Array(40), "table" : {}, "button" : {}};
  this.monthLen = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
  this.dateBase = new Array(40);

  this.format   = "yyyy-MM-dd";
  this.binder   = null;
  this.target   = null;
  this.usePopup = true;
  this.yearSpan = 60;
  this.now = new Date();
  this.min = -8640000000000000;
  this.max =  8640000000000000;
  try{this.object = window.createPopup();}catch(e){this.usePopup = false;}
  if(typeof(this.object)=="undefined")
  {
    this.usePopup = false;
    document.write("<iframe id='MzCalendarIframe' name='MzCalendarIframe' scrolling='no'"+
      " frameborder='0' style='position:absolute;z-index:999;width:0;height:0;'></iframe>");
    this.initIframe();
  }
  else
  {
    var style = this.object.document.createStyleSheet();
    var rule  = "font-size: 9pt; cursor: default; font-family: "+ this.chinese["song"];
    style.addRule("TD", rule);
    style.addRule("INPUT", rule +"; border: 0px; height: 18; width: 18; cursor: pointer");
    style.addRule("TD.day", "font-weight: bold; height: 19");
    style.addRule("TD.day DIV", "height: 17; padding-top: 2px");
    style.addRule(".selected", rule +"; background-color: #0A246A; color: #FFFFFF");
    style.addRule(".layer", rule +"; position: absolute; z-index: 1; top: 3");
    this.object.document.body.innerHTML = this.InitHTML();
  }
}
//init iframe
MzCalendar.prototype.initIframe = function()
{
  if(typeof(this.object)=="undefined")
  {
    this.object = window.frames["MzCalendarIframe"];
    setTimeout(this.id +".initIframe()", 10);
    return false;
  }
  else
  {
    try
    {
      this.object.document.write(this.InitHTML());
      this.object.document.close();
      this.iframe = GetElementById("MzCalendarIframe");
    }
    catch(e){setTimeout(this.id +".initIframe()", 10);}
  }
};
//previous year
MzCalendar.prototype.prevYear   = function()
{
  if(new Date(this.curYear, 0, 0).getTime()>this.min)
  {
    this.datetime.setFullYear(this.curYear - 1); this.write();
  }
};
//next year
MzCalendar.prototype.nextYear   = function()
{
  if(new Date(this.curYear, 12, 1).getTime()<this.max)
  {
    this.datetime.setFullYear(this.curYear + 1); this.write();
  }
};
//previous month
MzCalendar.prototype.prevMonth  = function()
{
  if(new Date(this.curYear, this.curMonth, 0).getTime()>this.min)
  {
    this.datetime.setMonth(this.curMonth - 1); this.write();
  }
};
//next month
MzCalendar.prototype.nextMonth  = function()
{
  if(new Date(this.curYear, (this.curMonth + 1), 1).getTime()<this.max)
  {
    this.datetime.setMonth(this.curMonth + 1); this.write();
  }
};
//hide calendar layer
MzCalendar.prototype.hide = function()
{
  if(this.usePopup) this.object.hide();
  else this.iframe.style.height = "0";
  this.target.blur();
};
//HTMLElement.getElementById extend
function GetElementById(id)
{
  if (typeof(id) != "string" || id == "") return null;
  if (document.getElementById) return document.getElementById(id);
  if (document.all) return document.all(id);
  try {return eval(id);} catch(e){ return null;}
};
//show calendar layer
MzCalendar.prototype.show       = function(binder)
{
  if(!binder){alert("Binder is wrong!"); return false;}0
  this.now = new Date();
  try
  {
    if(typeof(this.elements["body"])=="undefined")
    {
      this.elements["body"] = this.object.document;
      this.setElement();
    }
    this.elements["button"]["today"].title = this.now.Format(this.format);
    this.elements["divYear"].style.display  = "none";
    this.elements["divMonth"].style.display = "none";
  }
  catch(e){}
  this.write();

  try{
    var e = binder, x = e.offsetLeft, y = e.offsetTop;
    while(e=e.offsetParent){x += e.offsetLeft; y += e.offsetTop;}
    var DL = document.body.scrollLeft, DT = document.body.scrollTop;

    if(this.usePopup)
    {
      var SH = window.screen.height, b = binder.offsetHeight;
      if (SH -(window.screenTop + y +b - DT) < 191) b = -191;
      this.object.show(0, b, 144, 191, binder);
    }
    else
    {
      var DW = document.body.clientWidth;
      var DH = document.body.clientHeight;
      if (DT + DH - y - binder.offsetHeight< 191 && y - DT > 191)
        this.iframe.style.top = y - 191 + "px";
      else this.iframe.style.top = y + binder.offsetHeight + "px";
      if (x + 144 > DL + DW) this.iframe.style.left = DW + DL - 144 + "px";
      else if (x - DL < 0) this.iframe.style.left = DL + "px";
      else this.iframe.style.left = x + "px";
      this.iframe.style.width = 144 + "px";
      this.iframe.style.height = 191 + "px";
    }
  }
  catch(e){alert("Your browser unsupport offsetParent or clientWidth!");}
};
//print date into calendar
MzCalendar.prototype.write = function()
{
  var y = this.curYear  = this.datetime.getFullYear();
  var m = this.curMonth = this.datetime.getMonth();
  var d = this.curDay   = this.datetime.getDate();
  var w = new Date(y, m, 1).getDay();
  this.monthLen[1] = (0==y%4 && (0!=y%100 || 0==y%400)) ? 29 : 28;

  try
  {
    this.elements["year"].innerHTML  = y +" &#24180;";
    var mm = ("00"+ (m + 1)).substr(((m + 1) +"").length);
    this.elements["month"].innerHTML = mm +" &#26376;";
  }
  catch(e){ this.setElement()};

  var PMLength = 0==m  ? this.monthLen[11] : this.monthLen[m-1];
  for(var i=w; i>0; i--) this.dateBase[i-1] = new Date(y, m-1, PMLength - w + i);
  for(var i=0; i<this.monthLen[m]; i++) this.dateBase[i+w] = new Date(y, m, i+1);
  for(var k=i + w; k<40; k++) this.dateBase[k] = new Date(y, m+1, k - (i + w - 1));

  for(var i=0; i<40; i++)
  {
    var time = this.dateBase[i];
    if (time.getTime() < this.min || time.getTime() > this.max) var str = "&nbsp;"; else
    {
      var str = "<div onclick='parentNode.style.backgroundColor=parentNode.style.color"+
        "=\"\"; parent."+ this.id +".returnDate("+ i +")' style='";
      //weekend
      if(0==time.getDay() || 6==time.getDay()) str+="color:"+this.colors["weekend"]+";";
      //Is not current month
      if(m != time.getMonth()) str += "color: "+ this.colors["wordDark"] +";";
      //focus day
      if (this.inputdatetime
        && time.getFullYear() == this.inputdatetime.getFullYear()
        && time.getMonth()  == this.inputdatetime.getMonth()
        && time.getDate()   == this.inputdatetime.getDate())
        str += "background-color: "+ this.colors["dark"]
            +"; color:"+ this.colors["light"] +";";
      //today
      if(time.getFullYear() == this.now.getFullYear()
        && time.getMonth()  == this.now.getMonth()
        && time.getDate()   == this.now.getDate())
        str += "background-color: "+ this.colors["today"]
            +"; color:"+ this.colors["light"] +";";

      str += "' title='"+ time.Format(this.format) +
             "' align='center'>"+ this.dateBase[i].getDate(); +"</div>";
    }

    this.elements["days"][i].innerHTML = str;
  }
};
//event year select onchange
MzCalendar.prototype.selYearChange = function(e)
{
  this.elements["divYear"].style.display = "none";
  var yyyy = parseInt(e.innerHTML, 10);
  if (yyyy != this.datetime.getFullYear())
  {
    this.datetime.setFullYear(yyyy);
    this.write();
    this.elements["year"].innerHTML = e.innerHTML;
  }
};
//event month select onchange
MzCalendar.prototype.selMonthChange = function(e)
{
  this.elements["divMonth"].style.display = "none";
  var mm = parseInt(e.innerHTML, 10);
  if (mm != (this.datetime.getMonth() + 1))
  {
    this.datetime.setMonth(mm - 1);
    this.write();
    this.elements["month"].innerHTML = e.innerHTML;
  }
};
//[extended method] Date.toString by format string
Date.prototype.Format = function(format) //author: meizz
{
  var o = {
    "M+" : this.getMonth()+1, //month
    "d+" : this.getDate(),    //day
    "h+" : this.getHours(),   //hour
    "H+" : this.getHours(),   //hour
    "m+" : this.getMinutes(), //minute
    "s+" : this.getSeconds(), //second
    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
    "w" :  "日一二三四五六".indexOf(this.getDay()),  //week
    "S" : this.getMilliseconds() //millisecond
  }
  if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
      RegExp.$1.length==1 ? o[k] : 
        ("00"+ o[k]).substr((""+ o[k]).length));
  return format;
};
//return user checked date
MzCalendar.prototype.returnDate   = function(i)
{
  if(arguments.length==0 || typeof(i)!="number") var time = new Date();
  else var time = this.dateBase[i];
  if(this.target && this.target.tagName=="INPUT")
    this.target.value = time.Format(this.format);
  else alert("Cannot evaluate, because the target element is wrong!")
	if (this.target.getAttribute("Event"))
	{
		eval(this.target.getAttribute("Event"));
	}
  this.hide();
};
//set min date(yyyy/MM/dd[ hh:mm[:ss]])
MzCalendar.prototype.setMin   = function(datestr)
{
  var n = Date.parse(datestr);
  if(!isNaN(n) && n < this.max) this.min = n;
  else alert(datestr +" isn't Date String![setMin method]");
}
//set max date(yyyy/MM/dd[ hh:mm[:ss]])
MzCalendar.prototype.setMax   = function(datestr)
{
  var n = Date.parse(datestr);
  if(!isNaN(n) && n > this.min) this.max = n;
  else alert(datestr +" isn't Date String![setMax method]");
}
//fill in year div layer
MzCalendar.prototype.fillYear = function()
{
  this.curYear = this.datetime.getFullYear();
  var div = this.elements["divYear"];
  var n   = Math.floor(this.yearSpan/2);
  var min = new Date(this.min).getFullYear();
  if(this.curYear - n > min) min = this.curYear - n;
  var max = new Date(this.max).getFullYear();
  if (min + this.yearSpan < max) max = min + this.yearSpan;

  if(div.childNodes.length > 0)
  {
    var n = div.childNodes.length;
    for(var i=min; i<=max && i-min<n; i++)
    {
      var dc = div.childNodes[i-min];
      dc.innerHTML = i +" &#24180;";
      dc.className = "";
      if(i==this.curYear)
      {
        try
        {
          this.elements["divYear"].style.top = -1;
          dc.scrollIntoView(true);
          this.elements["divYear"].style.top = 3;
        }catch(e){}
        dc.className = "selected";
      }
    }
  }
  else
  {
    var str = "";
    for(var i=min; i<=max; i++)
    {
      str += "<div align='right' style='padding-right: 1px' onclick='parent."+ this.id +
        ".selYearChange(this)' onmouseover='this.style.backgroundColor=parent."+ this.id +
        ".colors[\"dark\"]; this.style.color=parent."+ this.id +".colors[\"light\"]' "+
        "onmouseout='style.backgroundColor=style.color=\"\"'>"+ i +" &#24180;</div>";
    }
    div.innerHTML = str;
  }
};
MzCalendar.prototype.fillMonth = function()
{
  this.curMonth = this.datetime.getMonth();
  var div = this.elements["divMonth"];
  if(div.childNodes.length > 0)
  {
    for(var i=div.childNodes.length - 1; i>=0; i--)
      div.childNodes[i].className = "";
    div.childNodes[this.curMonth].className = "selected";
  }
  else
  {
    var str = "";
    for(var i=1; i<13; i++)
    {
      str += "<div align='right' style='padding-right: 3px' onclick='parent."+ this.id +
        ".selMonthChange(this)' onmouseover='this.style.backgroundColor=parent."+ this.id +
        ".colors[\"dark\"]; this.style.color=parent."+ this.id +".colors[\"light\"]' "+
        "onmouseout='style.backgroundColor=style.color=\"\"'>"+ 
        ("00"+i).substr((i+"").length) +" &#26376;</div>";
    }
    div.innerHTML = str;
  }
}
MzCalendar.prototype.InitHTML   = function()
{
  var BK = /(MSIE)|(Netscape)/i.test(window.navigator.userAgent);
  var PM = (BK ? " value='3' style='font-family: Webdings' " : " value='&lt;' ");
  var NM = (BK ? " value='4' style='font-family: Webdings' " : " value='&gt;' ");
  var PY = (BK ? " value='7' style='font-family: Webdings' " : " value='&lt;&lt;' ");
  var NY = (BK ? " value='8' style='font-family: Webdings' " : " value='&gt;&gt;' ");
  var s  = "", id = this.id;   //Base HTML

  s += "<form name='meizz'>"+
    "<div id='divYear'  class='layer' style='z-index: 2; left: 22; width: 66;"+
    " height: 140; display: none; overflow: auto'></div>"+
    "<div id='divMonth' class='layer' style='z-index: 2; left: 84; width: 38;"+
    " height: 170; display: none'></div>"+
    "<div id='areaYear'  align='right' class='layer'"+
    " title='&#28857;&#20987;&#24555;&#36895;&#36873;&#25321;&#24180;&#20221;'"+
    " style='cursor: pointer; left: 22; width: 64; height: 18; padding-top: 2'"+
    " onmouseout='style.backgroundColor=style.color=\"\"'></div>"+
    "<div id='areaMonth' align='right' class='layer'"+
    " title='&#28857;&#20987;&#24555;&#36895;&#36873;&#25321;&#26376;&#20221;'"+
    " style='cursor: pointer; left: 86; width: 36; height: 18; padding: 2 3 0 0'"+
    " onmouseout='style.backgroundColor=style.color=\"\"'></div>";

  s += "<table id='tabMain' border='0' cellpadding='0' cellspacing='2' width='140'>"+
    "<tr><td height='18'>"+
    " <table id='tabHead' width='140' border='0' cellspacing='1' cellpadding='0'>"+
    " <tr align='right'><td width='18'><input type='button' name='btPrevMonthHead' "+
    "onclick='parent."+ id +".prevMonth()' "+ PM +" onfocus='this.blur()' "+
    "title='&#21521;&#21069;&#32763;&#19968;&#26376;'></td>"+
    " <td width='100' height='18'></td><td width='18'><input type='button' "+
    "title='&#21521;&#21518;&#32763;&#19968;&#26376;' name='btNextMonthHead' "+
    "onclick='parent."+ id +".nextMonth()' onfocus='blur()' "+ NM +">"+
    "</td></tr></table></td></tr>";

  s += "<tr><td height='18'><table width='140' height='18' "+
    "id='tabWeek' cellpadding='0' border='0' cellspacing='0'><tr align=center>"+
    "<td>&#26085;</td><td>&#19968;</td><td>&#20108;</td><td>&#19977;</td>"+
    "<td>&#22235;</td><td>&#20116;</td><td>&#20845;</td></tr></table></td></tr>";

  s += "<tr><td height='120' id='areaDay'><table border='0' "+
    "id='tabDay' height='120' width='140' cellspacing='1' cellpadding='0'>";
  for(var i=0; i<5; i++)
  {
    s += "<tr>";
    for(var y=0; y<7; y++) s += "<td class='day'></td>";
    s += "</tr>";
  }
  s += "<tr>"; for(var i=0; i<5; i++) s += "<td class='day'></td>";
  s += "<td colspan=2 title='"+ "regInfo" +"' align='right'><input type='button' "+
    "name='btClose' style='padding-top: 2px; width: 38; height: 19' "+
    "onclick='parent."+ id +".hide()' value='&#20851; &#38381;' tabindex='1'>"+
    "</td></tr></table></td></tr>";

  s += "<tr><td height='18'>"+
    "<table id='tabBtn' border='0' width='100%' cellpadding='0' cellspacing='1'><tr>"+
    "  <td  width='18'><input type='button' onfocus='this.blur()' "+
    "name='btPrevYear' onclick='parent."+ id +".prevYear()' "+ PY +
    "  title='&#21521;&#21069;&#32763;&#19968;&#24180;'></td>"+
    "  <td  width='18'><input type='button' onfocus='this.blur()' "+
    "name='btPrevMonth' onclick='parent."+ id +".prevMonth()' "+ PM +
    "  title='&#21521;&#21069;&#32763;&#19968;&#26376;'></td>"+
    "  <td height='18' width='64'><input type='button' onfocus='this.blur()' "+
    "name='btToday' value='&#20170; &#22825;' onclick='parent."+ id +
    ".returnDate()' style='padding-top: 2px; width: 100%'></td>"+
    "  <td  width='18'><input type='button' onfocus='this.blur()' "+
    "name='btNextMonth' onclick='parent."+ id +".nextMonth()' "+ NM +
    "  title='&#21521;&#21518;&#32763;&#19968;&#26376;'></td>"+
    "  <td  width='18'><input type='button' onfocus='this.blur()' "+
    "name='btNextYear'  onclick='parent."+ id +".nextYear()' "+ NY +
    "  title='&#21521;&#21518;&#32763;&#19968;&#24180;'></td></tr>"+
    "</table></td></tr></table></form>";
  if(!this.usePopup)
  {
    s="<html><head><meta http-equiv='Content-Type' content='text/html; charset=gb2312'>"+
      "\r\n<style type='text/css'>INPUT, SELECT, TD, DIV"+
      "{font-size: 9pt; cursor: default; font-family: "+ this.chinese["song"] +"}"+
      "INPUT{border: 0px; height: 18; width: 18; cursor: pointer}"+
      "TD.day{font-weight: bold; height: 19}"+
      "TD.day DIV{height: 15; padding-top: 2px}"+
      ".layer{position: absolute; z-index: 1; top: 3}"+
      ".selected{background-color: #0A246A; color: #FFFFFF}</style>\r\n"+
      "<script language='javascript'><!--\r\nwindow.get = function(id){"+
      "if (document.getElementById) return document.getElementById(id); "+
      "if (document.all) return document.all(id); "+
      "try {return eval(id);} catch(e){ return null;}};\r\n//--><\/script></head>"+
      "<body style='margin:0; border:0'>"+ s +"</body></html>";
  }
  return s;
};
//set calendar color
MzCalendar.prototype.setColor   = function(color)
{
  if(typeof(color)=="object")
    for (var i in color)
      this.colors[i] = color[i];
  var tabs = this.elements["table"];
  this.object.document.body.style.backgroundColor = this.colors["dark"];
  with(tabs["week"].style)
  {
    borderTop = borderLeft = "1px solid "+ this.colors["dark"];
    borderRight = borderBottom = "1px solid "+ this.colors["light"];
  }
  for(var i=tabs["week"].rows[0].cells.length-1; i>=0; i--)
  {
    var td = tabs["week"].rows[0].cells[i].style;
    td.color =  this.colors["light"];
    td.paddingTop = "2px";
    td.borderTop = td.borderLeft = "1px solid "+ this.colors["light"];
    td.borderRight = td.borderBottom = "1px solid "+ this.colors["dark"];
  }
  for (var i in this.elements["button"])
  {
    var button = this.elements["button"][i].style;
    button.color = this.colors["light"];
    button.backgroundColor = this.colors["dark"];
  }
  tabs["head"].style.backgroundColor = this.colors["light"];
  tabs["button"].style.backgroundColor = this.colors["light"];
  tabs["day"].style.backgroundColor = this.colors["dayBgColor"];
  for (var i=this.elements["days"].length-1; i>=0; i--)
  {
    var td = this.elements["days"][i].style;
    td.borderTop = td.borderLeft = "1px solid "+ this.colors["borderDark"];
    td.borderRight = td.borderBottom = "1px solid "+ this.colors["light"];
  }
  this.elements["divYear"].style.backgroundColor  = this.colors["light"];
  this.elements["divMonth"].style.backgroundColor = this.colors["light"];
  this.elements["divYear"].style.border  = "1px solid "+ this.colors["dark"];
  this.elements["divMonth"].style.border = "1px solid "+ this.colors["dark"];
};
//Object.attachEvent extend
function AttachEvent(object, eventName, Function, cancelBubble)
{
  var cb=cancelBubble?true:false;eventName=eventName.toLowerCase();
  if(document.attachEvent) object.attachEvent(eventName, Function);
  else object.addEventListener(eventName.substr(2), Function,  cb);
};
//get calendar children element
MzCalendar.prototype.setElement   = function()
{
  this.iframe = GetElementById("MzCalendarIframe");
  var doc = this.object.document;
  var tabs= this.elements["table"];
  var btns= this.elements["button"];
  var form= doc.forms["meizz"];
  if (this.usePopup)
  {
    var tds = doc.all.tabDay.getElementsByTagName("TD");
    tabs["main"] = doc.all.tabMain;
    tabs["head"] = doc.all.tabHead;
    tabs["week"] = doc.all.tabWeek;
    tabs["day"]  = doc.all.tabDay;
    tabs["button"] = doc.all.tabBtn;
    this.elements["year"] = doc.all.areaYear;
    this.elements["month"] = doc.all.areaMonth;
    this.elements["divYear"] = doc.all.divYear;
    this.elements["divMonth"] = doc.all.divMonth;
  }
  else
  {
    var tds = this.object.get("tabDay").getElementsByTagName("TD");
    tabs["main"] = this.object.get("tabMain");
    tabs["head"] = this.object.get("tabHead");
    tabs["week"] = this.object.get("tabWeek");
    tabs["day"]  = this.object.get("tabDay");
    tabs["button"] = this.object.get("tabBtn");
    this.elements["year"] = this.object.get("areaYear");
    this.elements["month"] = this.object.get("areaMonth");
    this.elements["divYear"] = this.object.get("divYear");
    this.elements["divMonth"] = this.object.get("divMonth");
    AttachEvent(document, "onclick", function(e)
    {
      e = e || window.event;  var element = e.target || e.srcElement;
      var height = parseInt(WebCalendar.iframe.style.height, 10);
      if(height>0 && WebCalendar.binder!=element) WebCalendar.hide();
    });
    try
    {
      AttachEvent(window, "onresize", function(){WebCalendar.hide();});
      AttachEvent(window, "onscroll", function(){WebCalendar.hide();});
    }
    catch(e){}
  }
  this.fillYear();
  this.fillMonth();
  var me = this;
  tabs["main"].onclick = function()
  {
    me.elements["divYear"].style.display  = "none";
    me.elements["divMonth"].style.display = "none";
  }
  this.elements["year"].onclick = function()
  {
    me.elements["divYear"].style.display  = "";
    me.elements["divMonth"].style.display = "none";
    WebCalendar.fillYear();
  }
  this.elements["month"].onclick = function()
  {
    me.elements["divYear"].style.display  = "none";
    me.elements["divMonth"].style.display = "";
    WebCalendar.fillMonth();
  }
  for(var i=0; i<40; i++)
  {
    this.elements["days"][i] = tds[i];
    tds[i].onmouseover = function()
    {
      this.style.backgroundColor = WebCalendar.colors["dark"];
      this.style.color = WebCalendar.colors["light"];
    }
    tds[i].onmouseout = function()
    {
      this.style.backgroundColor = this.style.color = "";
    }
    this.elements["days"][i].innerHTML = (i+1);
  }
  btns["PMH"] = form.btPrevMonthHead;
  btns["NMH"] = form.btNextMonthHead;
  btns["PY"]  = form.btPrevYear;
  btns["PM"]  = form.btPrevMonth;
  btns["NM"]  = form.btNextMonth;
  btns["NY"]  = form.btNextYear;
  btns["today"] = form.btToday;
  btns["close"] = form.btClose;
  this.elements["year"].onmouseover = function()
  {
    this.style.backgroundColor = WebCalendar.colors["dark"];
    this.style.color = WebCalendar.colors["light"];
  }
  this.elements["month"].onmouseover = function()
  {
    this.style.backgroundColor = WebCalendar.colors["dark"];
    this.style.color = WebCalendar.colors["light"];
  }
  this.setColor(null);
};
//calendar starting
MzCalendar.prototype.start = function(binder, target, format)
{
  if(arguments.length==0 || typeof(binder)!="object")
  {
    try{this.binder = this.target = event.srcElement;}
    catch(e){alert("parameter is wrong!"); return false;}
  }
  else
  {
    if(target)
    {
      if(typeof(target)=="object")this.target = target;
      else if(typeof(target)=="string"){this.format = target; this.target = binder;}
    }
    else this.target = binder;
    if (format && typeof(format)=="string") this.format = format;
    if (binder && typeof(binder)=="object") this.binder = binder;
  }
  this.datetime = new Date(); this.inputdatetime = null;
  if(this.target && this.target.value && /[^\s\x09]/.test(this.target.value))
  {
    var value = this.target.value.replace(/(^[\s\x09　]*)|([　\x09\s]*$)/, "");
    if(!isNaN(Date.parse(value)))
    {
      this.datetime      = new Date(Date.parse(value));
      this.inputdatetime = new Date(Date.parse(value));
    }
    else if(value != "")
    {
      var r1 = /^(\d{2,4})\D?(\d{1,2})\D?(\d{1,2})\D?$/;
      var r2 = /^(\d{1,2})\D?(\d{1,2})\D?(\d{2,4})\D?$/, str = "";
      var r3 = /^(\d{2,4})\D?(\d{1,2})\D?(\d{1,2})\D?(\d{1,2})\D?(\d{1,2})\D?(\d{1,2})?\D?$/;
      if(r1.test(value)) str = RegExp.$1 +"/"+ RegExp.$2 +"/"+ RegExp.$3;
      else if(r2.test(value)) str = RegExp.$3 +"/"+ RegExp.$1 +"/"+ RegExp.$2;
      else if(r3.test(value)) str = RegExp.$1 +"/"+ RegExp.$2 +"/"+ RegExp.$3
        +" "+ RegExp.$4 +":"+ RegExp.$5 +":"+ RegExp.$6;
      if(!isNaN(Date.parse(str)))
      {
        this.datetime      = new Date(Date.parse(str));
        this.inputdatetime = new Date(Date.parse(str));
      }
    }
  }

  if(typeof(this.elements["body"])=="undefined")
  {
    this.elements["body"] = this.object.document;
    this.setElement();
  }
  this.show(this.binder);
};

window.WebCalendar = new MzCalendar("WebCalendar");
