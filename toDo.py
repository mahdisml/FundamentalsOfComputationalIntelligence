import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.lines as mlines
import numpy as np

def newline(p1, p2):
    ax = plt.gca()
    xmin, xmax = ax.get_xbound()

    if(p2[0] == p1[0]):
        xmin = xmax = p1[0]
        ymin, ymax = ax.get_ybound()
    else:
        ymax = p1[1]+(p2[1]-p1[1])/(p2[0]-p1[0])*(xmax-p1[0])
        ymin = p1[1]+(p2[1]-p1[1])/(p2[0]-p1[0])*(xmin-p1[0])

    l = mlines.Line2D([xmin,xmax], [ymin,ymax])
    ax.add_line(l)
    return l

df = pd.read_excel(r'input2.xlsx')

w1 =0
w2 =0
w3 =0
dw1 = 0
dw2 = 0
dw3 = 0
teta = 0.2
alpha = 1
y = 0
changed = False
x1 = df['x'].tolist()
x2 = df['y'].tolist()
x3 = df['t'].tolist()
sizeOfData = 4

while (changed == False):
    for i in range(0,sizeOfData):
        if (teta < w1 * x1[i] + w2 * x2[i] * w3):
            y = 1
        if (-teta <= w1 * x1[i] + w2 * x2[i] * w3 <= teta):
            y = 0
        if (-teta > w1 * x1[i] + w2 * x2[i] * w3):
            y = -1
        if (y != x3[i]):
            dw1 = alpha * x3[i] * x1[i]
            dw2 = alpha * x3[i] * x2[i]
            dw3 = alpha * x3[i]
        else:
            dw1 = 0
            dw2 = 0
            dw3 = 0  
        print (dw1,dw2,dw3)
    changed = True

#x = np.linspace(0,10)
#y = x**2
#p1 = [1,20]
#p2 = [6,70]
#newline(p1,p2)
#plt.plot(x, y)

#plt.show()
