/* Global variables */
var size = 22;
var z = 0;
var xAmount = 25;
var yAmount = 25;
var zAmount = 1;
var offset = 90;
var timeOut = 500;
var firstPopulationAmount = 100;
var run = 1;
var lastPopulation;
var brickBodyColor = 0x042D2F;
var brickBorderColor = 0x060E0E;
var cubeBodyColor = 0xF490E0;
var populationCounter = 1;
var point;
var pixelView;
var brick;
var cube;
/* */

function generalInit() {
    var canvas = $('#canvas');
    point = new obelisk.Point(offset + yAmount * size, zAmount * size);
    pixelView = new obelisk.PixelView(canvas, point);

    var brickColor = new obelisk.SideColor(brickBorderColor, brickBodyColor);
    var brickDimension = new obelisk.BrickDimension(size, size);
    brick = new obelisk.Brick(brickDimension, brickColor);

    var cubeDimension = new obelisk.CubeDimension(size, size, size);
    var cubeColor = new obelisk.CubeColor().getByHorizontalColor(cubeBodyColor);
    cube = new obelisk.Cube(cubeDimension, cubeColor, true);
}

$(function() {
    generalInit();
    generatePlayingField(xAmount, yAmount);
    firstPopulation();
});

function firstPopulation() {
    var request = $.ajax({
        type: "GET",
        url: "/api/rest/population/" + firstPopulationAmount + "/random/" + xAmount
    });
    request.done(function( data ) {
        lastPopulation = data;
        renderRequest(data);
//        alert(JSON.stringify(data));
        console.log(JSON.stringify(data));
        window.setTimeout(continueDoing, timeOut);
    });
}

function nextPopulation() {
//    alert('beforeSend:' + JSON.stringify(lastPopulation))
    var request = $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(lastPopulation),
        url: "/api/rest/population/next"
    });
    request.done(function( data ) {
//        alert("before set new");
        lastPopulation = data;
        pixelView.clear();
        generatePlayingField(xAmount, yAmount);
        renderRequest(data);
        window.setTimeout(continueDoing, timeOut);
    });
}

function continueDoing() {
    if(run == 1) {
        nextPopulation();
//        alert("equ1");
    } else
    {
//        alert("NotEqual1");
    }

}

function renderRequest(data) {
    var cells = data['cellList'];
    for (var i = 0; i < cells.length; i++) {
        pixelView.renderObject(cube, new obelisk.Point3D(size * cells[i]['row'], size * cells[i]['column'], 0));
    }
    $('#population-cntr').text(populationCounter++);
}

function generatePlayingField(xAmount, yAmount) {
    var point3D = create2dArray(xAmount, yAmount);
    var x = 0;
    var y = 0;
    for(var i = 0; i < xAmount; i++) {
        x = i * size;
        for(var j = 0; j < yAmount; j++) {
            y = j * size;
            point3D[i, j] = new obelisk.Point3D(x, y, z);
            pixelView.renderObject(brick, point3D[i, j]);
        }
    }
}
function create2dArray(xSize, ySize) {
    var array2D = new Array(xSize);
    for(var i = 0; i < xSize; i++) {
        array2D[i] = new Array(ySize);
    }
    return array2D;
}
$('#run-btn').click(function(){
    run = 1;
    nextPopulation();
});

$('#stop-btn').click(function(){
    run = 0;
});

$('#restart-btn').click(function(){
    location.reload();
});