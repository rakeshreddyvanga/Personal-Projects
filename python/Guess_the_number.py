# template for "Guess the number" mini-project
# input will come from buttons and an input field
# all output for the game will be printed in the console

# import statements for the project
import simplegui
import random
import math

# global variables
secret_number = 0
guess_number = 0
max_range = 100
remaining_chances = 0

# helper function to start and restart the game
def new_game():
    # initialize global variables used in your code here
    global secret_number
    secret_number = random.randrange(0, max_range)
    print "New game. The range is from 0 to ", max_range
    calculate_chances()
    print "Number of chances remaining ", remaining_chances
    print ""
      

# helper function to exit from the game
def exit():
    # stop the frame
    frame.stop()

# define event handlers for control panel
def range100():
    # button that changes the range to [0,100) and starts a new game 
    global max_range
    max_range = 100
    new_game()
    

def range1000():
    # button that changes the range to [0,1000) and starts a new game     
    global max_range
    max_range = 1000
    new_game()
   
    
def input_guess(guess):
    # main game logic goes here	
    global guess_number, secret_number, remaining_chances
    guess_number = int(guess)
    print "Guess was ", guess_number
    if remaining_chances == 1:
         remaining_chances -= 1
         print "Number of chances remaining ", remaining_chances
         if secret_number == guess_number:
            print "Correct!"
            print ""
            new_game()
         else:
            print "You ran out of chances. The correct number is", secret_number
            print ""
            new_game()
    else:
        if  secret_number > guess_number:
            remaining_chances -= 1
            print "Higher."
            print "Number of chances remaining ", remaining_chances
            print ""
        elif secret_number < guess_number:
            print "Lower."
            remaining_chances -= 1
            print "Number of chances remaining ", remaining_chances
            print ""
        else:
            print "Correct!"
            print ""
            new_game()

def calculate_chances():
    global remaining_chances
    remaining_chances = int (math.ceil (math.log(max_range + 1) / math.log (2)))
    
# create frame
frame = simplegui.create_frame("Guess the number", 300 , 300)

# register event handlers for control elements and start frame
frame.add_button("Range: 0 - 100", range100, 200)
frame.add_button("Range: 0 - 1000", range1000, 200)
frame.add_button("Restart", new_game, 200)
frame.add_button("Exit", exit, 200)
frame.add_input("Enter your guess", input_guess, 200)

#start the frame
frame.start()

# call new_game 
new_game()


# always remember to check your completed program against the grading rubric
