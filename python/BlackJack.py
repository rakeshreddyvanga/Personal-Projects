# Mini-project #6 - Blackjack

import simplegui
import random

# load card sprite - 936x384 - source: jfitz.com
CARD_SIZE = (72, 96)
CARD_CENTER = (36, 48)
card_images = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/cards_jfitz.png")

#Canvas Measurements
CANVAS_WIDTH = 600
CANVAS_HEIGHT = 600
LOST_MSG = "You Lost."
WON_MSG = "You Won."

CARD_BACK_SIZE = (72, 96)
CARD_BACK_CENTER = (36, 48)
card_back = simplegui.load_image("http://storage.googleapis.com/codeskulptor-assets/card_jfitz_back.png")    

# initialize some useful global variables
in_play = False
outcome = ""
score = 0
message = "New Deal?"

# define globals for cards
SUITS = ('C', 'S', 'H', 'D')
RANKS = ('A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K')
VALUES = {'A':1, '2':2, '3':3, '4':4, '5':5, '6':6, '7':7, '8':8, '9':9, 'T':10, 'J':10, 'Q':10, 'K':10}


# define card class
class Card:
    def __init__(self, suit, rank):
        if (suit in SUITS) and (rank in RANKS):
            self.suit = suit
            self.rank = rank
        else:
            self.suit = None
            self.rank = None
            print "Invalid card: ", suit, rank

    def __str__(self):
        return self.suit + self.rank

    def get_suit(self):
        return self.suit

    def get_rank(self):
        return self.rank

    def draw(self, canvas, pos):
        card_loc = (CARD_CENTER[0] + CARD_SIZE[0] * RANKS.index(self.rank), 
                    CARD_CENTER[1] + CARD_SIZE[1] * SUITS.index(self.suit))
        canvas.draw_image(card_images, card_loc, CARD_SIZE, [pos[0] + CARD_CENTER[0], pos[1] + CARD_CENTER[1]], CARD_SIZE)
        
# define hand class
class Hand:
    def __init__(self):
        self.cards = []

    def __str__(self):
        result = "Hand contains "
        for card in self.cards:
            result += str(card)
            result += " "
        return result

    def add_card(self, card):
        self.cards.append(card)

    def get_value(self):
        # count aces as 1, if the hand has an ace, then add 10 to hand value if it doesn't bust
        hasAce = False
        value = 0
        for card in self.cards:
            c = card.get_rank()
            if c == 'A':
                hasAce = True
            value += VALUES[c]
        if hasAce:
            if value + 10 <= 21:
                return value + 10
            else:
                return value
        else:
            return value
   
    def draw(self, canvas, pos):
        disp_x = pos[0]
        for card in self.cards:
            card.draw(canvas, [disp_x, pos[1]])
            disp_x += CARD_SIZE[0] + 3
        
# define deck class 
class Deck:
    def __init__(self):
        self.deck = []
        for suit in SUITS:
            for rank in RANKS:
                self.deck.append(Card(suit, rank))

    def shuffle(self):
        # shuffle the deck 
        random.shuffle(self.deck)

    def deal_card(self):
        return self.deck.pop(-1)
    
    def count(self):
        return len(self.deck)
    
    def __str__(self):
        result = "Deck contains "
        for card in self.deck:
            result += str(card)
            result += " "
        return result   
    
    
#define event handlers for buttons
def deal():
    global outcome, in_play, DECK, PLAYER_HAND, DEALER_HAND, message, score
    if in_play:
        outcome = LOST_MSG
        score -= 1
        message = "New Deal?"
        in_play = False
    else:
        outcome = ""
        message = "Hit or Stand?"
        PLAYER_HAND = Hand()
        DEALER_HAND = Hand()
        if DECK.count() < 8:
            DECK = Deck()
        
        DECK.shuffle()
        for i in range(2):
            c1 = DECK.deal_card()
            PLAYER_HAND.add_card(c1)
            
            c2 = DECK.deal_card()
            DEALER_HAND.add_card(c2)       
        in_play = True

def hit():
    global in_play, score, PLAYER_HAND, DECK, outcome, message
    if in_play:
        c1 = DECK.deal_card()
        PLAYER_HAND.add_card(c1)
        if PLAYER_HAND.get_value() > 21:
            outcome = "Busted! " + LOST_MSG
            in_play = False
            score -= 1 
            message = "New Deal?"
    # if the hand is in play, hit the player
   
    # if busted, assign a message to outcome, update in_play and score
       
def stand():
    global in_play, score, PLAYER_HAND, DECK, outcome, DEALER_HAND, message 
    if in_play:
        in_play = False
        message = "New Deal?"
        while DEALER_HAND.get_value() < 17:
            DEALER_HAND.add_card(DECK.deal_card())
        dealer_val = DEALER_HAND.get_value()
        player_val = PLAYER_HAND.get_value()
        
        if dealer_val > 21:
            outcome = "Dealer Busted! " + WON_MSG             
            score += 1
        else:
            if dealer_val >= player_val:
                outcome = LOST_MSG                
                score -= 1
            else:
                outcome = WON_MSG                
                score += 1                
    else:
        outcome = LOST_MSG
        
   
    # if hand is in play, repeatedly hit dealer until his hand has value 17 or more

    # assign a message to outcome, update in_play and score

# draw handler    
def draw(canvas):
    global in_play, score, PLAYER_HAND, DECK, outcome, DEALER_HAND
    # test to make sure that card.draw works, replace with your code below
    player_text_pos = [50, 170]
    dealer_text_pos = [50,400] 
    canvas.draw_text('BLACKJACK', [CANVAS_WIDTH/2 - 75, 45], 35, 'Black', 'monospace')
    canvas.draw_text('Player', player_text_pos, 25, 'Black', 'monospace')
    PLAYER_HAND.draw(canvas, [player_text_pos[0], player_text_pos[1] + 10])
    canvas.draw_text('Dealer', dealer_text_pos, 25, 'Black', 'monospace')
    DEALER_HAND.draw(canvas, [dealer_text_pos[0], dealer_text_pos[1] + 10])
    if in_play:
        canvas.draw_image(card_back, CARD_BACK_CENTER, CARD_BACK_SIZE, [dealer_text_pos[0] + CARD_BACK_CENTER[0], dealer_text_pos[1] + 10 + CARD_BACK_CENTER[1]], CARD_BACK_SIZE)
    
    canvas.draw_text('Score:' + str(score), [450, 100], 25, 'Black', 'monospace')
    canvas.draw_text(outcome, [280, player_text_pos[1]], 25, 'Black', 'monospace')
    canvas.draw_text(message, [280, 330] , 25, 'Black', 'monospace')


# initialization frame
frame = simplegui.create_frame("Blackjack", CANVAS_WIDTH, CANVAS_HEIGHT)
frame.set_canvas_background("Green")

#create buttons and canvas callback
frame.add_button("Deal", deal, 200)
frame.add_button("Hit",  hit, 200)
frame.add_button("Stand", stand, 200)
frame.set_draw_handler(draw)


# get things rolling

DECK = Deck()
deal()
frame.start()


# remember to review the gradic rubric