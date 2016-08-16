html
{
	font-family: "Tw Cen MT", "Meiryo UI", sans-serif ;
}

.myfont
{
    font-size: 100px;
    text-align: right;
}



h1
{
    margin-left: 30px;
}

h3
{
	margin-left: 20px;
}
.standard-block
{
    margin: 10px;
    border: 2px solid #38387C;
    
    border-radius: 10px;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
}

.standard-block-main
{
	position: relative;
	width: 90%;
    margin: 10px auto;
    border: 2px solid #38387C;
    height: 500px;
	overflow: hidden;
}

.footer
{
	display: block;
	margin-left: auto;
	margin-right: auto;
	margin-top: 30px;
	margin-bottom: 30px;
}

.flip
{
	position: absolute;
	transition: all 0.5s ease;
	width: 100%;
	height: 100%;
}

.flip.ng-enter {
	opacity: 0;
}
.flip.ng-leave {
	opacity: 1;
}
.flip.ng-enter-active {
	opacity: 1;
}
.flip.ng-leave-active {
	opacity: 0;
}

.backward.flip.ng-enter
{
	left: 100%;
}
.backward.flip.ng-enter-active {
    left: 0;
}
.backward.flip.ng-leave {
    left: 0;
}
.backward.flip.ng-leave-active {
    left: -100%;
}

.forward.flip.ng-enter
{
	left: -100%;
}
.forward.flip.ng-enter-active {
    left: 0;
}
.forward.flip.ng-leave {
    left: 0;
}
.forward.flip.ng-leave-active {
    left: 100%;
}


.pos-block-main-view
{
}

.startup-img
{
	/*中央寄せ*/
	display: block;
	margin-left: auto;
	margin-right:auto;
	/*中央寄せ*/
	
	margin-top: 90px;
}


.menu
{
	width: 600px;
	display: block;

	padding: 0px;
	margin-left: auto;
	margin-right: auto;
	
	/*border: 1px solid red;*/
}
.menu li
{
	display: inline-block;
	width: 182px;
	height: 190px;

	margin-left: 5px;
	margin-right: 5px;

    border: 2px solid #38387C;

	font-size: 24px;
	text-align: center;


}
.menu li a
{
	width: 182px;
	height: 110px;

	display: block;
	/* background-color: #92D050; */
	/*border: 1px solid red;*/

	margin: 0px;
	padding: 0px;
	padding-top: 80px;

	text-decoration: none;
	color: #000000;
}
.menu li a:active
{
    background-color: #B8B8DE;
}

.menu2
{
	width: 600px;

	display: block;

	padding: 0px;
	margin-left: auto;
	margin-right: auto;
}
.menu2 li
{
	width: 283px;
	height: 190px;

	display: inline-block;
    border: 2px solid #38387C;

	margin-left: 5px;
	margin-right: 5px;

	font-size: 24px;
	text-align: center;

}
.menu2 li a
{
	width: 283px;
	height: 110px;

	display: block;

	margin: 0px;
	padding: 0px;
	padding-top: 80px;

	text-decoration: none;
	color: #000000;
	/* background-color: #92D050; */
	/*border: 1px solid red;*/

}
.menu2 li a:active
{
    background-color: #B8B8DE;
}

#menu_back
{
    background-color: #B8B8DE;
    color: #FFFFFF;
}
#menu_back:active
{
    background-color: #7878AE;
    color: #FFFFFF;
}
#menu_stop
{
    background-color: #B8B8DE;
    color: #FFFFFF;
}
#menu_stop:active
{
    background-color: #7878AE;
    color: #FFFFFF;
}




.background-polling
{
	width: 500px;
	height: 280px;
	
	display: block;
	background-image: url("pict/kazasu.png");
	background-repeat: no-repeat;
	background-position-x: 50%;
	background-position-y: bottom;
	background-size: 60%;

	margin-left: auto;
	margin-right: auto;

	text-align: center;
	font-size: 300%;
	
	/*border: 1px solid red;*/
}


.menu_bottom
{
	/*width: 360px;*/

	display: block;
	overflow: hidden;
	
	margin-left: auto;
	margin-right: 0;
	padding: 0px;
	
	/*border: 1px solid red;*/
}
.menu_box1
{
	width: 120px;
}
.menu_box2
{
	width: 240px;
}
.menu_box3
{
	width: 360px;
}

.menu_bottom li
{
	width: 100px;
	height: 90px;

	display: inline-block;
	/*float: right;*/
    border: 2px solid #38387C;

	margin-left: 5px;
	margin-right: 5px;

	font-size: 24px;
	text-align: center;
}
.menu_bottom li a
{
	width: 100px;
	height: 60px;

	display: block;

	/* background-color: #92D050; */
	/*border: 1px solid red;*/
	
	margin: 0px;
	padding: 0px;
	padding-top: 30px;

	text-decoration: none;
	color: #000000;


}
.menu_bottom li a:active
{
    background-color: #B8B8DE;
}

.message_field
{
	width: 600px;
	height: 100px;
	
	margin-left: auto;
	margin-right: auto;
	
	font-size: 32pt;
	text-align: left;
	
	/*border: 1px solid red;*/
}

.message_field_error
{
	width: 500px;
	height: 280px;
	
	display: block;
	background-image: url("pict/error.png");
	background-repeat: no-repeat;
	background-position-x: 50%;
	background-position-y: bottom;
	background-size: 45%;

	margin-left: auto;
	margin-right: auto;

	text-align: center;
	font-size: 300%;
	
	/*border: 1px solid red;*/
}

.errorcode_field
{
	float: left;
	width: 85px;
	height: 100px;

	font-size: 28pt;
	/*border: 1px solid red;*/
}
.message_field_1
{
	float: right;
	width: 514px;
	height: 50px;

	/*border: 1px solid red;*/
}
.message_field_2
{
	float: right;
	width: 514px;
	height: 50px;

	/*border: 1px solid red;*/
}




.input_field
{
	width: 660px;
	height: 180px;
	
	margin-left: auto;
	margin-right: auto;
	
	clear: both;
	
	font-size: 86pt;
	text-align: center;

	padding: 0px;

	/*border: 1px solid red;*/
}


.input_field_title
{
	width: 125px;

	display: inline-block;


	font-size: 36pt;
	text-align:left;
	
	/*border:1px solid blue;*/
}
.yen_field
{
	width: 100px;
	
	display: inline-block;


	
	margin: 0px;
	padding: 0px;

	display: inline;
	/*border: 1px solid red;*/

	font-size: 64pt;
	text-align: center;
}
.number_field
{
	width: 350px;

	display: inline-block;


	margin: 0px;
	padding: 0px;

	/*border: 1px solid red;*/

	font-size: 64pt;
	text-align: right;
}
