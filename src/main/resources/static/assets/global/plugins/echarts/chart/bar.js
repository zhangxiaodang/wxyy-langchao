/**
 * Created by Administrator on 2019/7/11.
 */
/**
 * Created by Administrator on 2019/7/10.
 */

//横向柱状图
var horizontalBarDefaultData = {
    id: "test",         //显示柱状图的id
    grid:[80, 30, 80, 40],
    //图例数据
    legend:{
        show:false,
        data:[],
        orient:"vertical",
        x : "top",
        y : "center",
        itemGap:10,
        textStyle: {
            color: '#fff'          //legend字体颜色
        }
    },
    //x坐标轴 横向柱状图x轴数据不需要设定，根据柱状图数据获取
    xAxis:{
        name:""
    },
    //y坐标轴数据
    yAxis:{
        name:"",
        data:[]
    },
    //柱状图数据
    seriesArray:{
        name: [],
        type: 'bar',
        data: [],
        barWidth: 15,
        barGap: 10,
        itemStyle: {
            emphasis: {},
            normal: {
                label: {
                    show: false,
                    position: 'right',
                    offset: [5, -2],
                    textStyle: {
                        color: '#F68300',
                        fontSize: 10
                    }
                },
                color:[]
            }
        }
    }
};

//横向柱状图
var verticalBarDefaultData = {
    id: "test",         //显示柱状图的id
    grid:[80, 30, 80, 40],
    //图例数据
    legend:{
        show:false,
        data:[],
        orient:"vertical",
        x : "top",
        y : "center",
        itemGap:10,
        textStyle: {
            color: '#fff'          //legend字体颜色
        }
    },
    //x坐标轴 横向柱状图x轴数据不需要设定，根据柱状图数据获取
    xAxis:{
        name:"",
        data:[]
    },
    //y坐标轴数据
    yAxis:{
        name:""
    },
    //柱状图数据
    seriesArray:{
        name: [],
        type: 'bar',
        data: [],
        barWidth: 15,
        barGap: 0,
        itemStyle: {
            emphasis: {},
            normal: {
                label: {
                    show: false,
                    textStyle: {
                        color: '#F68300',
                        fontSize: 10
                    }
                },
                color:[]
            }
        }
    }
};

function horizontalBar(para){
    var myChart = echarts.init(document.getElementById(para.id));
    var data = horizontalBarDefaultData;
    dataSet(data, para);
    var series = [];
    for(var i = 0; i < data.seriesArray.data.length; i++){
        var name = "";
        var color = "";
        if(data.seriesArray.name.length > i){
            name = data.seriesArray.name[i];
        }
        if(data.seriesArray.itemStyle.normal.color.length > i){
            color = data.seriesArray.itemStyle.normal.color[i];
        }
        var se = {
            name: name,
            type: 'bar',
            data: data.seriesArray.data[i],
            barWidth: data.seriesArray.barWidth,
            barGap: data.seriesArray.barGap,
            itemStyle: {
                emphasis: data.seriesArray.itemStyle.emphasis,
                normal: {
                    label: data.seriesArray.itemStyle.normal.label,
                    color: color
                }
            }
        };

        series.push(se);
    }
    var option = {
        tooltip: {
            trigger: 'axis',
            textStyle:{
                fontSize : '10'
            },
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: data.legend,
        grid: {
            x: data.grid[0],
            y: data.grid[1],
            x2: data.grid[2],
            y2: data.grid[3],
            backgroundColor: 'rgba(0,0,0,0)',
            borderWidth: 0,
            borderColor: '#ccc'
        },
        xAxis: {
            type: 'value',
            name: data.xAxis.name,
            nameTextStyle:{
                color:  '#fff'
            },
            axisLine:{                      //坐标轴轴线相关设置。
                show:false,                  //是否显示坐标轴轴线。
                lineStyle:{
                    width:1,                   //坐标轴线线宽。
                    color:'#435984'                //颜色。
                }
            },
            axisLabel: {
                show: false,
                interval: 0,
                textStyle: {
                    color: '#fff'
                }
            },
            splitArea: {
                show: false
            },
            axisTick:{
                show:false
            },
            splitLine: {
                show: true,
                //  改变轴线颜色
                lineStyle: {
                    // 使用深浅的间隔色
                    color:  '#435984',
                    type:'dotted'
                }
            }
        },
        //y轴的数据表示这些为一组数据
        yAxis: {
            type: 'category',
            name: data.yAxis.name,   //员工号
            nameTextStyle:{
                color:  '#fff'
            },
            data: data.yAxis.data,
            splitArea: {
                show: false
            },
            axisTick:{
                show:false
            },
            axisLine:{                      //坐标轴轴线相关设置。
                show:false,                //是否显示坐标轴轴线。
                lineStyle:{
                    width:2,                   //坐标轴线线宽。
                    color:'#435984'                //颜色。
                }
            },
            axisLabel: {
                show: true,
                interval: 0,
                textStyle: {
                    color: '#fff'
                }
            },
            splitLine: {
                show: true,
                //  改变轴线颜色
                lineStyle: {
                    // 使用深浅的间隔色
                    color:  '#1c4f86',
                    type:'dotted'
                }
            }
        },
        backgroundColor:'rgba(11, 22, 51, 0)',
        //series是一个数组，数组的一个元素对应yAxis的这一组数据
        series: series
    };
    myChart.setOption(option);
}

function verticalBar(para){
    var myChart = echarts.init(document.getElementById(para.id));
    var data = verticalBarDefaultData;
    dataSet(data, para);
    var series = [];
    for(var i = 0; i < data.seriesArray.data.length; i++){
        var name = "";
        var color = "";
        if(data.seriesArray.name.length > i){
            name = data.seriesArray.name[i];
        }
        if(data.seriesArray.itemStyle.normal.color.length > i){
            color = data.seriesArray.itemStyle.normal.color[i];
        }
        var se = {
            name: name,
            type: 'bar',
            data: data.seriesArray.data[i],
            barWidth: data.seriesArray.barWidth,
            barGap: data.seriesArray.barGap,
            itemStyle: {
                emphasis: data.seriesArray.itemStyle.emphasis,
                normal: {
                    label: data.seriesArray.itemStyle.normal.label,
                    color: color
                }
            }
        };

        series.push(se);
    }
    var option = {
        tooltip: {
            trigger: 'axis',
            textStyle:{
                fontSize : '10'
            },
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: data.legend,
        grid: {
            x: data.grid[0],
            y: data.grid[1],
            x2: data.grid[2],
            y2: data.grid[3],
            backgroundColor: 'rgba(0,0,0,0)',
            borderWidth: 0,
            borderColor: '#ccc'
        },
        xAxis: {
            type: 'category',
            name: data.xAxis.name,
            data: data.xAxis.data,
            nameTextStyle:{
                color:  '#fff'
            },
            axisLine:{                      //坐标轴轴线相关设置。
                show:false,                  //是否显示坐标轴轴线。
                lineStyle:{
                    width:1,                   //坐标轴线线宽。
                    color:'#435984'                //颜色。
                }
            },
            axisLabel: {
                show: true,
                textStyle: {
                    color: '#fff'
                }
            },
            splitArea: {
                show: false
            },
            axisTick:{
                show:false
            },
            splitLine: {
                show: true,
                //  改变轴线颜色
                lineStyle: {
                    // 使用深浅的间隔色
                    color:  '#435984',
                    type:'dotted'
                }
            }
        },
        //y轴的数据表示这些为一组数据
        yAxis: {
            type: 'value',
            name: data.yAxis.name,
            nameTextStyle:{
                color:  '#fff'
            },
            splitArea: {
                show: false
            },
            axisTick:{
                show:false
            },
            axisLine:{                      //坐标轴轴线相关设置。
                show:false,                //是否显示坐标轴轴线。
                lineStyle:{
                    width:2,                   //坐标轴线线宽。
                    color:'#435984'                //颜色。
                }
            },
            axisLabel: {
                show: true,
                interval: 0,
                textStyle: {
                    color: '#fff'
                }
            },
            splitLine: {
                show: true,
                //  改变轴线颜色
                lineStyle: {
                    // 使用深浅的间隔色
                    color:  '#1c4f86',
                    type:'dotted'
                }
            }
        },
        backgroundColor:'rgba(11, 22, 51, 0)',
        //series是一个数组，数组的一个元素对应yAxis的这一组数据
        series: series
    };
    myChart.setOption(option);
}
