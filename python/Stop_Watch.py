# template for "Stopwatch: The Game"

import simplegui

# define global variables
tenth_second = 0
stops_x = 0
stops_y = 0
timer_running = False

# define helper function format that converts time
# in tenths of seconds into formatted string A:BC.D
def format(t):
    ret = ""
    if t < 10:
        ret = "0:00."+ str(t)
    elif t >= 10 and t < 600:
        sec = t/10
        tenth_sec = t % 10
        if sec < 10:
            ret =  "0:0" + str(sec)+ "." + str(tenth_sec)
        else:
            ret =  "0:" + str(sec)+ "." + str(tenth_sec)
    else:
        mins = t / 600
        secs = t % 600
        sec = secs / 10
        tenth_sec = secs % 10
        if sec < 10:
            ret =  str(mins) + ":0" + str(sec)+ "." + str(tenth_sec)
        else:
            ret =  str(mins) + ":" + str(sec)+ "." + str(tenth_sec)
    
    return ret
    
# define event handlers for buttons; "Start", "Stop", "Reset"
def start():
    global timer_running
    timer.start()
    timer_running = True
    
def stop():
    global stops_x, stops_y, timer_running
    if timer_running:
        timer.stop()
        timer_running = False
        stops_y += 1
        if tenth_second % 10 == 0:
            stops_x += 1
               

def reset():
    global tenth_second, stops_y, stops_x, timer_running
    timer.stop()
    timer_running = False
    tenth_second = 0
    stops_y = 0
    stops_x = 0
    
    
# define event handler for timer with 0.1 sec interval
def tick():
    global tenth_second
    tenth_second += 1

# define draw handler
def draw(canvas):
    canvas.draw_text(format(tenth_second),[115, 100], 32, "White")
    canvas.draw_text(str(stops_x) + "/" + str(stops_y),[260, 20], 20, "Yellow")
    
# create frame
frame = simplegui.create_frame("Stop Watch", 300, 200)
timer = simplegui.create_timer(100, tick)

# register event handlers
frame.set_draw_handler(draw)
frame.add_button("Start", start, 150)
frame.add_button("Stop", stop, 150)
frame.add_button("Reset", reset, 150)

# start frame
frame.start()

# Please remember to review the grading rubric
