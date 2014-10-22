# Implementation of classic arcade game Pong

import simplegui
import random

# initialize globals - pos and vel encode vertical info for paddles
WIDTH = 600
HEIGHT = 400       
BALL_RADIUS = 20
PAD_WIDTH = 8
PAD_HEIGHT = 80
HALF_PAD_WIDTH = PAD_WIDTH / 2
HALF_PAD_HEIGHT = PAD_HEIGHT / 2
LEFT = False
RIGHT = True
PADDLE_VELOCITY = 5.0


# initialize ball_pos and ball_vel for new bal in middle of table
# if direction is RIGHT, the ball's velocity is upper right, else upper left
def spawn_ball(direction):
    global ball_pos, ball_vel # these are vectors stored as lists
    ball_pos = [WIDTH/2, HEIGHT/2]
    horizontal_vel = (random.randrange(120, 240) / 60.0)  #divide by fps
    vertical_vel = - (random.randrange(60, 180) / 60.0)	  #divide by fps
    if direction: #direction == RIGHT
        ball_vel = [horizontal_vel, vertical_vel]
    else: #direction == LEFT
        ball_vel = [-horizontal_vel, vertical_vel]

# define event handlers
def new_game():
    global paddle1_pos, paddle2_pos, paddle1_vel, paddle2_vel  # these are numbers
    global score1, score2  # these are ints
    spawn_ball(RIGHT)
    score1 = 0
    score2 = 0
    paddle1_pos = HEIGHT/2.0
    paddle2_pos = HEIGHT/2.0
    paddle1_vel = 0.0
    paddle2_vel = 0.0

def draw(canvas):
    global score1, score2, paddle1_pos, paddle2_pos, ball_pos, ball_vel, paddle1_vel, paddle2_vel
    
    # Top and Bottom edges check & updating the velocity
    if ball_pos[1] < BALL_RADIUS:
        ball_vel[1] = -ball_vel[1]
    if ball_pos[1] > HEIGHT - BALL_RADIUS - 1:
        ball_vel[1] = -ball_vel[1]
    
    #Check if the ball position collides with the left & right gutters
    if ball_pos[0] < (BALL_RADIUS + PAD_WIDTH):
        if ball_pos[1] >= (paddle1_pos - HALF_PAD_HEIGHT) and ball_pos[1] <= (paddle1_pos + HALF_PAD_HEIGHT):
            ball_vel[0] = - (ball_vel[0] + (ball_vel[0] * 0.1))
            ball_vel[1] += ball_vel[1] * 0.1            
        else:    
            spawn_ball(RIGHT)
            score2 += 1
            
    if ball_pos[0] > (WIDTH - PAD_WIDTH - BALL_RADIUS - 1):
        if ball_pos[1] >= (paddle2_pos - HALF_PAD_HEIGHT) and ball_pos[1] <= (paddle2_pos + HALF_PAD_HEIGHT):
            ball_vel[0] = - (ball_vel[0] + (ball_vel[0] * 0.1))
            ball_vel[1] += ball_vel[1] * 0.1
        else:
            spawn_ball(LEFT)
            score1 += 1
        
    #Updating the ball position
    ball_pos[0] += ball_vel[0]
    ball_pos[1] += ball_vel[1]
  
       
        
    # draw mid line and gutters
    canvas.draw_line([WIDTH / 2, 0],[WIDTH / 2, HEIGHT], 1, "White")
    canvas.draw_line([PAD_WIDTH, 0],[PAD_WIDTH, HEIGHT], 1, "White")
    canvas.draw_line([WIDTH - PAD_WIDTH, 0],[WIDTH - PAD_WIDTH, HEIGHT], 1, "White")
        
    # update ball
            
    # draw ball
    canvas.draw_circle(ball_pos, BALL_RADIUS, 0.1, 'White', 'White')
    
    # update paddle's vertical position, keep paddle on the screen  
    paddle1_pos += paddle1_vel
    paddle2_pos += paddle2_vel
    
    if paddle1_pos - HALF_PAD_HEIGHT <= 0:
            paddle1_vel = 0
    if paddle1_pos + HALF_PAD_HEIGHT > HEIGHT - 1:
            paddle1_vel = 0
    
    if paddle2_pos - HALF_PAD_HEIGHT <= 0:
            paddle2_vel = 0
    if paddle2_pos + HALF_PAD_HEIGHT > HEIGHT - 1:
            paddle2_vel = 0
    
    # draw paddles
    canvas.draw_line([HALF_PAD_WIDTH, paddle1_pos - HALF_PAD_HEIGHT ], [HALF_PAD_WIDTH, paddle1_pos + HALF_PAD_HEIGHT], PAD_WIDTH, 'White') #left paddle
    canvas.draw_line([WIDTH - HALF_PAD_WIDTH , paddle2_pos - HALF_PAD_HEIGHT], [WIDTH - HALF_PAD_WIDTH, paddle2_pos + HALF_PAD_HEIGHT], PAD_WIDTH, 'White') # right paddle
    
    # draw scores
    canvas.draw_text(str(score1), (200, 70), 70, 'White', 'monospace')
    canvas.draw_text(str(score2), (400, 70), 70, 'White', 'monospace')
        
def keydown(key):
    global paddle1_vel, paddle2_vel
    if key == simplegui.KEY_MAP['w']:
        if paddle1_pos - HALF_PAD_HEIGHT <= 0:
            paddle1_vel = 0
        else:
            paddle1_vel = -PADDLE_VELOCITY
    if key == simplegui.KEY_MAP['s']:
        if paddle1_pos + HALF_PAD_HEIGHT > HEIGHT - 1:
            paddle1_vel = 0
        else:
            paddle1_vel = PADDLE_VELOCITY
            
    if key == simplegui.KEY_MAP['up']:
        if paddle2_pos - HALF_PAD_HEIGHT <= 0:
            paddle2_vel = 0
        else:
            paddle2_vel = -PADDLE_VELOCITY
            
    if key == simplegui.KEY_MAP['down']:
        if paddle2_pos + HALF_PAD_HEIGHT > HEIGHT - 1:
            paddle2_vel = 0
        else:
            paddle2_vel = PADDLE_VELOCITY
   
def keyup(key):
    global paddle1_vel, paddle2_vel
    
    if key == simplegui.KEY_MAP['w']:
        paddle1_vel = 0.0
    if key == simplegui.KEY_MAP['s']:
        paddle1_vel = 0.0
    if key == simplegui.KEY_MAP['up']:
        paddle2_vel = 0.0
    if key == simplegui.KEY_MAP['down']:
        paddle2_vel = 0.0

#restart handler       
def restart():
    new_game()

# create frame
frame = simplegui.create_frame("Pong", WIDTH, HEIGHT)
frame.set_draw_handler(draw)
frame.set_keydown_handler(keydown)
frame.set_keyup_handler(keyup)
frame.add_button('Restart', restart, 100)


# start frame
new_game()
frame.start()
