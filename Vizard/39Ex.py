import viz

viz.go()

model = viz.add('art/wall.ive')

viz.MainView.setPosition(0, 0.68, -1.3)

stone = viz.addTexture('art/stone wall.jpg')
needle = viz.addTexture('art/pine needles.jpg')

model.setPosition(0,-0.5,0)

model.texture(stone,'',0)
model.texture(needle,'',1)

matrix = vizmat.Transform()
matrix.setScale(2,2)
matrix.setTrans(0,0)

model.texmat(matrix, '',0)

stone.wrap(viz.WRAP_S, viz.REPEAT)
stone.wrap(viz.WRAP_T, viz.MIRROR)

slider = viz.addSlider()
slider.setPosition(.5,.1)

def text_swap(slider_position):
	model.texblend(slider_position,'',1)
	
vizact.onslider(slider, text_swap)
text_swap(0)