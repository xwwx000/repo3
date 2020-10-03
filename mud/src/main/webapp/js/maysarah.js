
function headNav(n,m){
	for(i=1;i<=m;i++)
	{
		document.getElementById('info'+i).style.display='none';
		document.getElementById('headNav'+i).className='none';
	}
	document.getElementById('info'+n).style.display='block';
	document.getElementById('headNav'+n).className='headNavCur';
}

function changinquireTab(n,m){
	for(i=1;i<=m;i++)
	{
		document.getElementById('inquireInfo'+i).style.display='none';
		document.getElementById('inquireTab'+i).className='none';
	}
	document.getElementById('inquireInfo'+n).style.display='block';
	document.getElementById('inquireTab'+n).className='cur';
}

function carStatistics(n,m)
{
	for(i=1;i<=m;i++)
	{
		document.getElementById('statisticsInfo'+i).style.display='none';
		document.getElementById('carStatisticsTab'+i).className='none';
	}
	document.getElementById('statisticsInfo'+n).style.display='block';
	document.getElementById('carStatisticsTab'+n).className='cur';
}

function Show_Hidden(trid){
	if(trid.style.display=="none"){
		trid.style.display='';
		document.getElementById('switch').style.backgroundImage='url(../images/direction-right.png)';
	}else{
		trid.style.display='none';
		document.getElementById('switch').style.backgroundImage='url(../images/direction-left.png)';
	}
}

function fullScreen(){
	var mapBox = document.getElementById('mapBox');
	var mapDiv = document.getElementById('mapDiv');
	var conText = document.getElementById('fullScreen').innerHTML;
	if(conText == "全屏显示"){
		document.getElementById('switch').style.display='none';
		mapBox.style.position = 'absolute';
		mapBox.style.width = '100%';
		mapBox.style.height = '100%';
		mapBox.style.top = 0;
		mapBox.style.left = 0;
		mapDiv.style.height='100%';
		document.getElementById('fullScreen').innerHTML = "退出全屏";
	}
	else{
		document.getElementById('switch').style.display='block';
		mapBox.style.position = 'relative';
		mapBox.style.width = '100%';
		mapBox.style.height = 533+'px';
		mapBox.style.top = 0;
		mapBox.style.left = 0;
		document.getElementById('fullScreen').innerHTML = "全屏显示";
		getContentSize();
	}
}