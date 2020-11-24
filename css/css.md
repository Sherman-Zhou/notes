 

## 相对长度：
- em	它是描述相对于应用在当前元素的字体尺寸，所以它也是相对长度单位。一般浏览器字体大小默认为16px，则2em == 32px；
- rem	rem 是根 em（root em）的缩写，rem作用于非根元素时，相对于根元素字体大小；rem作用于根元素字体大小时，相对于其出初始字体大小
- vw	viewpoint width，视窗宽度，1vw=视窗宽度的1%
- vh	viewpoint height，视窗高度，1vh=视窗高度的1%
- vmin	vw和vh中较小的那个 
- vmax	vw和vh中较大的那个 
- %	 

## 绝对长度
- px  像素 (1px = 1/96th of 1in) 像素或许被认为是最好的"设备像素"，而这种像素长度和你在显示器上看到的文字屏幕像素无关。px实际上是一个按角度度量的单位。
- pt point，大约1/72英寸； (1pt = 1/72in)

## background:
1. background-color
2. background-image
3. background-repeat
4. background-attachment
5. background-position
6. background-size: length|percentage|cover|contain;  (css3)

## text: 
- color	设置文本颜色
- direction	设置文本方向。
- letter-spacing	设置字符间距
- line-height	设置行高
- text-align	对齐元素中的文本
- text-decoration	向文本添加修饰
- text-indent	缩进元素中文本的首行
- text-shadow	设置文本阴影
- text-transform	控制元素中的字母
- unicode-bidi	设置或返回文本是否被重写 
- vertical-align	设置元素的垂直对齐
- white-space	设置元素中空白的处理方式
- word-spacing	设置字间距
   
 ## Font

- font	在一个声明中设置所有的字体属性
- font-family	指定文本的字体系列
- font-size	指定文本的字体大小
- font-style	指定文本的字体样式
- font-variant	以小型大写字体或者正常字体显示文本。
- font-weight	指定字体的粗细。					
 
 ## Link

- a:link - 正常，未访问过的链接
- a:visited - 用户已访问过的链接
- a:hover - 当用户鼠标放在链接上时
- a:active - 链接被点击的那一刻

## ul
- list-style	简写属性。用于把所有用于列表的属性设置于一个声明中
- list-style-image	将图像设置为列表项标志。
- list-style-position	设置列表中列表项标志的位置。
- list-style-type	设置列表项标志的类型。

## Box Model
- Margin(外边距) - 清除边框外的区域，外边距是透明的。
- Border(边框) - 围绕在内边距和内容外的边框。
- Padding(内边距) - 清除内容周围的区域，内边距是透明的。
- Content(内容) - 盒子的内容，显示文本和图像。
```
div {
    width: 300px;
    border: 25px solid green;
    padding: 25px;
    margin: 25px;
}
//总元素的宽度=宽度+左填充+右填充+左边框+右边框+左边距+右边距
//总元素的高度=高度+顶部填充+底部填充+上边框+下边框+上边距+下边距
``` 

### border:
- border-width
- border-style (required)
- border-color
```
	border:5px solid red;
```

### 轮廓（outline）
 ```
 	outline:green dotted thick;
 ```

 ### margin
   - auto:  设置浏览器边距。这样做的结果会依赖于浏览器
   - length:  定义一个使用百分比的边距(px|em|rem)
   - %
 ```
	margin-top:100px;
	margin-bottom:100px;
	margin-right:50px;
	margin-left:50px;

	margin:25px 50px 75px 100px;
		上边距为25px
		右边距为50px
		下边距为75px
		左边距为100px

	margin:25px 50px 75px;
	上边距为25px
	左右边距为50px
	下边距为75px

	margin:25px 50px;
	上下边距为25px
	左右边距为50px

	margin:25px;
	所有的4个边距都是25px

 ```

  ![box](box.png)

  ### padding
  - length:  定义一个使用百分比的边距(px|em|rem)
  - %

```
	padding-top:25px;
	padding-bottom:25px;
	padding-right:50px;
	padding-left:50px; 
```

## 尺寸 (Dimension)

- height	设置元素的高度。
- line-height	设置行高。
- max-height	设置元素的最大高度。
- max-width	设置元素的最大宽度。
- min-height	设置元素的最小高度。
- min-width	设置元素的最小宽度。
- width	设置元素的宽度。

## Display
- display:block  -- 显示为块级元素
- display:inline  -- 显示为内联元素
- display:inline-block -- 显示为内联块元素，表现为同行显示并可修改宽高内外边距等属性

 display:none 元素不再占用空间。
 visibility: hidden 使元素在网页上不可见，但仍占用空间

## Position(定位) ( top, bottom, left, right)

- static:    HTML 元素的默认值，即没有定位，遵循正常的文档流对象。静态定位的元素不会受到 top, bottom, left, right影响
- relative:  相对定位元素的定位是相对其正常位置。移动相对定位元素，但它原本所占的空间不会改变 预留空间的元素仍保存在正常文档流
- fixed:     元素的位置相对于浏览器窗口是固定位置。 即使窗口是滚动的它也不会移动
- absolute : 绝对定位的元素的位置相对于最近的已定位父元素，如果元素没有已定位的父元素，那么它的位置相对于 html, 使元素的位置与文档流无关，因此不占据空间。absolute 定位的元素和其他元素重叠
- sticky: 在跨越特定阈值前为相对定位，之后为固定定位。这个特定阈值指的是 top, right, bottom 或 left 之一，换言之，指定 top, right, bottom 或 left 四个阈值其中之一，才可使粘性定位生效。否则其行为与***相对定位***相同

```
	div.sticky {
		position: -webkit-sticky; /* Safari */
		position: sticky;
		top: 0;
		background-color: green;
		border: 2px solid #4CAF50;
	}
```
 

  
| 属性 | 说明 | 
| :-----| :----|
| bottom	| 定义了定位元素下外边距边界与其包含块下边界之间的偏移 |
| clip	| 剪辑一个绝对定位的元素 |	 
| cursor |显示光标移动到指定的类型 | 
| left	| 定义了定位元素左外边距边界与其包含块左边界之间的偏移 | 
| overflow | 设置当元素的内容溢出其区域时发生的事情。| 
| overflow-y | 指定如何处理顶部/底部边缘的内容溢出元素的内容区域 | 
| overflow-x | 指定如何处理右边/左边边缘的内容溢出元素的内容区域 |
| position | 指定元素的定位类型 |
| right |	定义了定位元素右外边距边界与其包含块右边界之间的偏移 |
| top |	定义了一个定位元素的上外边距边界与其包含块上边界之间的偏移 |
| z-index |	设置元素的堆叠顺序	|

### z-index 属性指定了一个元素的堆叠顺序

### overflow

  ***注意:overflow 属性只工作于指定高度的块元素上***

| 值 |	描述 |
| :-----| :----|
| visible |	默认值。内容不会被修剪，会呈现在元素框之外。 |
| hidden |	内容会被修剪，并且其余内容是不可见的。|
| scroll |	内容会被修剪，但是浏览器会显示滚动条以便查看其余的内容。|
| auto | 如果内容被修剪，则浏览器会显示滚动条以便查看其余的内容。|
| inherit| 规定应该从父元素继承 overflow 属性的值。|

## Float(浮动)
元素的水平方向浮动，意味着元素只能左右移动而不能上下移动。

一个浮动元素会尽量向左或向右移动，直到它的外边缘碰到包含框或另一个浮动框的边框为止。

浮动元素之后的元素将围绕它。 

浮动元素之前的元素将不会受到影响。

如果图像是右浮动，下面的文本流将环绕在它左边

### clear
元素浮动之后，周围的元素会重新排列，为了避免这种情况，使用 clear 属性。

clear 属性指定元素两侧不能出现浮动元素。

## CSS 布局 - 水平 & 垂直对齐

### 元素居中对齐

要水平居中对齐一个元素(如 <div>), 可以使用 margin: auto;。

设置到元素的宽度将防止它溢出到容器的边缘。


元素通过指定宽度，并将两边的空外边距平均分配：

***注意: 如果没有设置 width 属性(或者设置 100%)，居中对齐将不起作用***

```
.center {
    margin: auto;
    width: 50%;
    border: 3px solid green;
    padding: 10px;
}
```

### 文本居中对齐
	.center {
		text-align: center;
		border: 3px solid green;
	}

### 图片居中对齐
	img {
		display: block;
		margin: auto;
		width: 40%;
	}

### 左右对齐 - 使用定位方式

注释：绝对定位元素会被从正常流中删除，并且能够交叠元素。

提示: 当使用 position 来对齐元素时, 通常 <body> 元素会设置 margin 和 padding 。 这样可以避免在不同的浏览器中出现可见的差异。

	.right {
		position: absolute;
		right: 0px;
		width: 300px;
		border: 3px solid #73AD21;
		padding: 10px;
	}

### 左右对齐 - 使用 float 方式
当像这样对齐元素时，对 body元素的外边距和内边距进行预定义是一个好主意。这样可以避免在不同的浏览器中出现可见的差异。

	.right {
		float: right;
		width: 300px;
		border: 3px solid #73AD21;
		padding: 10px;
	}

### 垂直居中 - 使用 position 和 transform

	.center { 
		height: 200px;
		position: relative;
		border: 3px solid green; 
	}
	
	.center p {
		margin: 0;
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
	}

## CSS3 边框

- border-radius
- box-shadow
- border-image

		#rcorners1 {
			border-radius: 25px;
			background: #8AC007;
			padding: 20px;
			width: 200px;
			height: 150px;
	   }

## CSS3 背景

- background-image: 多张图片
- background-size： background-size指定背景图像的大小。CSS3以前，背景图像大小由图像的实际大小决定。 CSS3中可以指定背景图片，让我们重新在不同的环境中指定背景图片的大小。您可以指定像素或百分比大小
- background-origin： 属性指定了背景图像的位置区域。
- background-clip

## CSS3 渐变（Gradients）
- 线性渐变（Linear Gradients）- 向下/向上/向左/向右/对角方向
- 径向渐变（Radial Gradients）- 由它们的中心定义

## CSS3 文本效果

| 属性|	描述|
| :-----| :----|
| hanging-punctuation|	规定标点字符是否位于线框之外。|
| punctuation-trim	|规定是否对标点字符进行修剪。|
| text-align-last	|设置如何对齐最后一行或紧挨着强制换行符之前的行|
| text-emphasis	|向元素的文本应用重点标记以及重点标记的前景色|
| text-justify	|规定当 text-align 设置为 "justify" 时所使用的对齐方法|
| text-outline	|规定文本的轮廓|
| text-overflow|	规定当文本溢出包含元素时发生的事情|
| text-shadow	|向文本添加阴影|
| text-wrap|	规定文本的换行规则|
| word-break	|规定非中日韩文本的换行规则|
| word-wrap	|允许对长的不可分割的单词进行分割并换行到下一行|

## CSS3 字体
 
Internet Explorer 9+, Firefox, Chrome, Safari, 和 Opera 支持 WOFF (Web Open Font Format) 字体.

Firefox, Chrome, Safari, 和 Opera 支持 .ttf(True Type字体)和.otf(OpenType)字体字体类型）。

Chrome, Safari 和 Opera 也支持 SVG 字体/折叠.

Internet Explorer 同样支持 EOT (Embedded OpenType) 字体.

注意： Internet Explorer 8 以及更早的版本不支持新的 @font-face 规则。


## CSS3 多列 

|属性|	描述|
| :-----| :----|
|column-count|	指定元素应该被分割的列数|
|column-fill|	指定如何填充列|
|column-gap|	指定列与列之间的间隙|
|column-rule|	所有 column-rule-* 属性的简写|
|column-rule-color|	指定两列间边框的颜色|
|column-rule-style|	指定两列间边框的样式|
|column-rule-width|	指定两列间边框的厚度|
|column-span|	指定元素要跨越多少列|
|column-width|	指定列的宽度|
|columns|	设置 column-width 和 column-count 的简写|

## box-sizing

|值	|说明|
| :-----| :----|
|content-box|	这是 CSS2.1 指定的宽度和高度的行为。指定元素的宽度和高度（最小/最大属性）适用于box的宽度和高度。元素的填充和边框布局和绘制指定宽度和高度除外|
|border-box|	指定宽度和高度（最小/最大属性）确定元素边框。也就是说，对元素指定宽度和高度包括了 padding 和 border 。通过从已设定的宽度和高度分别减去边框和内边距才能得到内容的宽度和高度。
|inherit|	指定 box-sizing 属性的值，应该从父元素继承


## CSS3 弹性盒子(Flex Box)

弹性盒子由弹性容器(Flex container)和弹性子元素(Flex item)组成。

弹性容器通过设置 display 属性的值为 flex 或 inline-flex将其定义为弹性容器。

弹性容器内包含了一个或多个弹性子元素。

注意： 弹性容器外及弹性子元素内是正常渲染的。弹性盒子只定义了弹性子元素如何在弹性容器内布局。

弹性子元素通常在弹性盒子内一行显示。默认情况每个容器只有一行。

如果我们设置 direction 属性为 rtl (right-to-left),弹性子元素的排列方式也会改变，页面布局也跟着改变:
```
body {
    direction: rtl;
}
 
.flex-container {
    display: -webkit-flex;
    display: flex;
    width: 400px;
    height: 250px;
    background-color: lightgrey;
}
 
.flex-item {
    background-color: cornflowerblue;
    width: 100px;
    height: 100px;
    margin: 10px;
}
```

|属性|	描述|
| :-----| :----|
|display|	指定 HTML 元素盒子类型。|
|flex-direction|	指定了弹性容器中子元素的排列方式|
|justify-content|	设置弹性盒子元素在主轴（横轴）方向上的对齐方式。|
|align-items|	设置弹性盒子元素在侧轴（纵轴）方向上的对齐方式。|
|flex-wrap|	设置弹性盒子的子元素超出父容器时是否换行。|
|align-content|	修改 flex-wrap 属性的行为，类似 align-items, 但不是设置子元素对齐，而是设置行对齐|
|flex-flow|	flex-direction 和 flex-wrap 的简写|
|order|	设置弹性盒子的子元素排列顺序。|
|align-self|	在弹性子元素上使用。覆盖容器的 align-items 属性。|
|flex|	设置弹性盒子的子元素如何分配空间。|

### flex-direction

flex-direction的值有:

- row：横向从左到右排列（左对齐），默认的排列方式。
- row-reverse：反转横向排列（右对齐，从后往前排，最后一项排在最前面。
- column：纵向排列。
- column-reverse：反转纵向排列，从后往前排，最后一项排在最上面。

### justify-content 属性

![](./flex_justify-content.jpg)



- flex-start：
弹性项目向行头紧挨着填充。这个是默认值。第一个弹性项的main-start外边距边线被放置在该行的main-start边线，而后续弹性项依次平齐摆放。

- flex-end：
弹性项目向行尾紧挨着填充。第一个弹性项的main-end外边距边线被放置在该行的main-end边线，而后续弹性项依次平齐摆放。

- center：
弹性项目居中紧挨着填充。（如果剩余的自由空间是负的，则弹性项目将在两个方向上同时溢出）。

- space-between：
弹性项目平均分布在该行上。如果剩余空间为负或者只有一个弹性项，则该值等同于flex-start。否则，第1个弹性项的外边距和行的main-start边线对齐，而最后1个弹性项的外边距和行的main-end边线对齐，然后剩余的弹性项分布在该行上，相邻项目的间隔相等。

- space-around：
弹性项目平均分布在该行上，两边留有一半的间隔空间。如果剩余空间为负或者只有一个弹性项，则该值等同于center。否则，弹性项目沿该行分布，且彼此间隔相等（比如是20px），同时首尾两边和弹性容器之间留有一半的间隔（1/2*20px=10px）

### align-items 属性
- flex-start：弹性盒子元素的侧轴（纵轴）起始位置的边界紧靠住该行的侧轴起始边界。
- flex-end：弹性盒子元素的侧轴（纵轴）起始位置的边界紧靠住该行的侧轴结束边界。
- center：弹性盒子元素在该行的侧轴（纵轴）上居中放置。（如果该行的尺寸小于弹性盒子元素的尺寸，则会向两个方向溢出相同的长度）。
- baseline：如弹性盒子元素的行内轴与侧轴为同一条，则该值与'flex-start'等效。其它情况下，该值将参与基线对齐。
- stretch：如果指定侧轴大小的属性值为'auto'，则其值会使项目的边距盒的尺寸尽可能接近所在行的尺寸，但同时会遵照'min/max-width/height'属性的限制。

## flex-wrap 属性
flex-wrap 属性用于指定弹性盒子的子元素换行方式。

- nowrap - 默认， 弹性容器为单行。该情况下弹性子项可能会溢出容器。
- wrap - 弹性容器为多行。该情况下弹性子项溢出的部分会被放置到新行，子项内部会发生断行
- wrap-reverse -反转 wrap 排列。

## align-content 属性
- stretch - 默认。各行将会伸展以占用剩余的空间。
- flex-start - 各行向弹性盒容器的起始位置堆叠。
- flex-end - 各行向弹性盒容器的结束位置堆叠。
- center -各行向弹性盒容器的中间位置堆叠。
- space-between -各行在弹性盒容器中平均分布。
- space-around - 各行在弹性盒容器中平均分布，两端保留子元素与子元素之间间距大小的一半

## 弹性子元素属性
- order: 排序
- 


## CSS3 多媒体查询

语法： 
```
@media mediatype and|not|only (media feature) {
    CSS-Code;
}
<link rel="stylesheet" media="mediatype and|not|only (media feature)" href="print.css">

```

###　媒体功能
|值	|描述|
| :-----| :----|
|aspect-ratio|	定义输出设备中的页面可见区域宽度与高度的比率|
|color|	定义输出设备每一组彩色原件的个数。如果不是彩色设备，则值等于0|
|color-index|	定义在输出设备的彩色查询表中的条目数。如果没有使用彩色查询表，则值等于0|
|device-aspect-ratio|	定义输出设备的屏幕可见宽度与高度的比率。|
|device-height|	定义输出设备的屏幕可见高度。|
|device-width|	定义输出设备的屏幕可见宽度。|
|grid|	用来查询输出设备是否使用栅格或点阵。|
|height|	定义输出设备中的页面可见区域高度。|
|max-aspect-ratio|	定义输出设备的屏幕可见宽度与高度的最大比率。|
|max-color|	定义输出设备每一组彩色原件的最大个数。|
|max-color-index|	定义在输出设备的彩色查询表中的最大条目数。|
|max-device-aspect-ratio|	定义输出设备的屏幕可见宽度与高度的最大比率。|
|max-device-height|	定义输出设备的屏幕可见的最大高度。|
|max-device-width|	定义输出设备的屏幕最大可见宽度。|
|max-height|	定义输出设备中的页面最大可见区域高度。|
|max-monochrome	|定义在一个单色框架缓冲区中每像素包含的最大单色原件个数。|
|max-resolution|	定义设备的最大分辨率。|
|max-width|	定义输出设备中的页面最大可见区域宽度。|
|min-aspect-ratio|	定义输出设备中的页面可见区域宽度与高度的最小比率。|
|min-color|	定义输出设备每一组彩色原件的最小个数。|
|min-color-index|	定义在输出设备的彩色查询表中的最小条目数。|
|min-device-aspect-ratio|	定义输出设备的屏幕可见宽度与高度的最小比率。|
|min-device-width|	定义输出设备的屏幕最小可见宽度。|
|min-device-height|	定义输出设备的屏幕的最小可见高度。|
|min-height|	定义输出设备中的页面最小可见区域高度。|
|min-monochrome	|定义在一个单色框架缓冲区中每像素包含的最小单色原件个数|
|min-resolution	|定义设备的最小分辨率。|
|min-width|	定义输出设备中的页面最小可见区域宽度。|
|monochrome|	定义在一个单色框架缓冲区中每像素包含的单色原件个数。如果不是单色设备，则值等于0|
|orientation|	定义输出设备中的页面可见区域高度是否大于或等于宽度。|
|resolution|	定义设备的分辨率。如：96dpi, 300dpi, 118dpcm|
|scan|	定义电视类设备的扫描工序。|
|width|	定义输出设备中的页面可见区域宽度。|