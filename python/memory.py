# implementation of card game - Memory

import simplegui
import random

idx_last = 0
idx_more_last = 0
counter = 0

# helper function to initialize globals
def new_game():
    global deck, exposed, state
    deck = range(0,8)
    deck.extend(range(0,8))
    random.shuffle(deck)
    state = 0
    exposed  = [False] * 16
    

     
# define event handlers
def mouseclick(pos):
    # add game state logic here
    global state, idx_last, idx_more_last, counter
    click_pos = list(pos)
    idx = click_pos[0] / 50
    if exposed[idx] == False:
        exposed[idx] = True
        if state == 0:
            idx_last = idx
            state = 1
        elif state == 1:
            state = 2
            idx_more_last = idx
			counter += 1
        else:
            state = 1
            
            exposed[idx] = True
            if deck[idx_last] != deck[idx_more_last]:
                exposed[idx_last] = False
                exposed[idx_more_last] = False
                
            idx_last = idx            
     
                        
# cards are logically 50x100 pixels in size    
def draw(canvas):
    x = 0
    label.set_text("Turns = " + str(counter))
    for i in range(0, 16):
        if exposed[i] == True:
            canvas.draw_text(str(deck[i]), [x + 15, 60], 35, "White", "monospace")
        else:
             canvas.draw_polygon([[x, 0], [x, 100], [x+50, 100], [x+50, 0]], 1, 'Black', 'Green')
        x += 50


# create frame and add a button and labels
frame = simplegui.create_frame("Memory", 800, 100)
frame.add_button("Reset", new_game)
label = frame.add_label("Turns = 0")

# register event handlers
frame.set_mouseclick_handler(mouseclick)
frame.set_draw_handler(draw)

# get things rolling
new_game()
frame.start()


# Always remember to review the grading rubric