/**
 * Created by Administrator on 2019/7/11.
 */
var pieDefaultData = {
    id:"",
    tooltip : {
        show: false,
        formatter: "{b} : {c}"
    },
    legend: {
        show: true,
        orient : 'horizontal',
        x : 'center',
        y : 'bottom',
        itemGap:5,
        itemHeight:10,
        data:[],
        textStyle: {
            color: '#000'          //legend字体颜色
        }
    },
    seriesArray:{
        name:[],
        type:'pie',
        clockWise:true,
        radius : [[45, 75]],
        data:[],
        itemStyle: {
            emphasis: {},
            normal: {
                label: {
                    align:"center",
                    show:true,
                    position: "outer",
                    formatter: "{c}({d})%",
                    textStyle: {
                        fontSize : '12',
                        color:'#000000',
                        backgroundColor:"#cccccc",
                        borderColor: '#449933',
                        borderRadius: 2,
                        padding:5
                    }
                },
                labelLine: {
                    show:true,
                    length:5,
                    lineStyle:{
                        width:2
                    }
                }
            }
        }
    },
    series:[]
};
function eChartsPie(para){
    var myChart = echarts.init(document.getElementById(para.id));
    var data = pieDefaultData;
    dataSet(data, para);
    var series = [];
    for(var i = 0; i < data.seriesArray.data.length; i++){
        var name = "";
        if(data.seriesArray.name.length > i){
            name = data.seriesArray.name[i];
        }
        var startAngle = 0;
        if(data.seriesArray.startAngle != undefined && data.seriesArray.startAngle.length > i){
            startAngle = data.seriesArray.startAngle[i];
        }
        var se = {
            name:name,
            startAngle:startAngle,
            type:'pie',
            clockWise:true,
            radius : data.seriesArray.radius[i],
            data:[],
            itemStyle: {
                emphasis: data.seriesArray.itemStyle.emphasis,
                normal: data.seriesArray.itemStyle.normal
            }
        };
        var pieData = data.seriesArray.data[i];
        for(var j = 0; j < pieData.value.length; j++){
            var color = "";
            if(pieData.color != undefined && pieData.color.length > j){
                color = pieData.color[j];
            }
            var itemStyle = {
                color:color
            };
            if(pieData.name[j] == "invisible"){
                itemStyle = {
                    normal : {
                        color: 'rgba(0,0,0,0)',
                        label: {show:false},
                        labelLine: {show:false}
                    },
                    emphasis : {
                        color: 'rgba(0,0,0,0)',
                        label: {show:false},
                        labelLine: {show:false}
                    },
                    color: color
                }
            }
            var da = {
                name: pieData.name[j],
                value: pieData.value[j],
                itemStyle: itemStyle
            };

            se.data.push(da);
        }
        series.push(se);
    }

    var option = {
        tooltip : data.tooltip,
        legend: data.legend,
        series : series
    };
    myChart.setOption(option);
}