
function OnInitPage()
{	//alert("dsf");
	var CalendarStart = document.getElementById("CalendarStart");
	var CalendarEnd = document.getElementById("CalendarEnd");
	

	CalendarStart.value = sDate[0];
	CalendarEnd.value = sDate[1];

//    SetCurrentNav("AnalyzeFund");
//    LoadNetValueChart(_Code_);
//    LoadNetValueList(_Code_);	
}

function LoadHistory()
{
//	var code = _Code_;
	var CalendarStart = document.getElementById("CalendarStart");
	var CalendarEnd = document.getElementById("CalendarEnd");
	var startDate = new Date(CalendarStart.value.replace("-",","));
	var endDate = new Date(CalendarEnd.value.replace("-",","));
	if (startDate == "Invalid Date") startDate = new Date(CalendarStart.value.replace("-",",").replace("-",","));
	if (endDate == "Invalid Date") endDate = new Date(CalendarEnd.value.replace("-",",").replace("-",","));
	if (startDate > endDate)
	{
		alert("");
		return false;
	}
	
	sDate[0] = CalendarStart.value;
	sDate[1] = CalendarEnd.value;
	/*
	if( curr == null || curr.id == "NetValue" )
	{
	    LoadNetValueChart(_Code_);
	}
	else
	{
	    LoadNetValueIncreaseChart(_Code_);
	}
	LoadNetValueList(_Code_);
	*/
}

function frameLoad()
{
	var divLoad = document.getElementById("divLoad");
	divLoad.style.display = "none";
}

function LoadNetValueChart(code)
{
    var url = "/ShowNetValueHistory.aspx?code=" + code + "&startdate=" + sDate[0] + "&enddate=" + sDate[1];
    var frm = document.getElementById("frmChartHistory");
    frm.src = url;
}

function LoadNetValueIncreaseChart(code)
{
    var url = "/NetValueIncreate.aspx?code=" + code + "&startdate=" + sDate[0] + "&enddate=" + sDate[1];
    var frm = document.getElementById("frmChartHistory");
    frm.src = url;
}

function LoadNetValueList(code)
{
    var url = "/NetValueHistoryList.aspx?code=" + code + "&startdate=" + sDate[0] + "&enddate=" + sDate[1];
    var frm = document.getElementById("netValueListHistory");
    frm.src = url;
}

var curr = null;
var exponents = [];
function SwitchChartDataSource(id)
{
    if( typeof(_Code_) != "undefined" )
    {
        var e = document.getElementById(id);
        if( curr == null )
        {
            curr = e;
            e.style.color="red";
            var anotherId = e.id == "NetValue" ? "Increase" : "NetValue";
            var e1 = document.getElementById(anotherId);
            e1.style.color = "black";
        }
        else
        {
            curr.style.color = "black";
            e.style.color="red";
            curr = e;
        }
        if( e.id == "NetValue" )
        {
            LoadNetValueChart(_Code_);
        }
        else
        {
            LoadNetValueIncreaseChart(_Code_);
        }
    }
}