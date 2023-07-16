'''
    Our Model class
    This should control the actual "logic" of your website
    And nicely abstracts away the program logic from your page loading
    It should exist as a separate layer to any database or data structure that you might be using
    Nothing here should be stateful, if it's stateful let the database handle it
'''
import rsa

import view
import random

import base64, os
import bcrypt
from sql import SQLDatabase

# Initialise our views, all arguments are defaults for the template
page_view = view.View()
db = SQLDatabase(database_arg="identifier.sqlite")
db.database_setup()
user_name_global = "null"

private_key = "-----BEGIN RSA PRIVATE KEY-----\n" + "MIIBPAIBAAJBAJXPd3FZqlVFx5smdsXbEdNY2WyOkpcAetTj+XeO1PzWs/UYT9nY\n" + "dkyJ3ff4lyQP/J4E1q7AsbjiqiUbUKx3jYECAwEAAQJAdnYyvggoP/vIxi/ZNcVw\n" + "SA53B3eKBSvU9Wk8SEVCDRK1bPKQy3z+zneQ8lSinXM0ovBvQIaFNa/8PfwpRapo\n" + "0QIjANjpe2E8MFGAlqomiVKKpH/D0d0tGWMnv2CXdZ1CjjAfku0CHwCwznlAv5yW\n" + "NF1Rqs4y1bZcJHSLmvZOHDYyvRKMLmUCIneBc3tn2MseiGOoJaI3Rlgp/9bWgRUz\n" + "Eepap+8DeykiTCUCHwCgDko2Ez/tufnAtJ915YHwaBAZUW8nxuJJjF/+BwECIkIa\n" + "fp+a03yUtkixbky2LiC802zWnoMjzn+8zAkTYXpSc4s=\n" + "-----END RSA PRIVATE KEY-----"


# -----------------------------------------------------------------------------
# Index
# -----------------------------------------------------------------------------

def index():
    '''
        index
        Returns the view for the index
    '''
    return page_view("index")


# -----------------------------------------------------------------------------
# register
# -----------------------------------------------------------------------------

def register_form():
    '''
        login_form
        Returns the view for the login_form
    '''
    return page_view("register")


def register_check(username, password, pub):
    '''
        login_form
        Returns the view for the login_form
    '''
    register = db.getUsername()
    for name in register:
        if username == name[0]:
            return page_view("invalid", reason="username already exists")
    # public_key, private_key = rsa.newkeys(1024)
    # pub = public_key.save_pkcs1().decode()
    # priv = private_key.save_pkcs1().decode()
    # private_key = "-----BEGIN RSA PRIVATE KEY-----\n" + "MIIBPAIBAAJBAJXPd3FZqlVFx5smdsXbEdNY2WyOkpcAetTj+XeO1PzWs/UYT9nY\n" + "dkyJ3ff4lyQP/J4E1q7AsbjiqiUbUKx3jYECAwEAAQJAdnYyvggoP/vIxi/ZNcVw\n" + "SA53B3eKBSvU9Wk8SEVCDRK1bPKQy3z+zneQ8lSinXM0ovBvQIaFNa/8PfwpRapo\n" + "0QIjANjpe2E8MFGAlqomiVKKpH/D0d0tGWMnv2CXdZ1CjjAfku0CHwCwznlAv5yW\n" + "NF1Rqs4y1bZcJHSLmvZOHDYyvRKMLmUCIneBc3tn2MseiGOoJaI3Rlgp/9bWgRUz\n" + "Eepap+8DeykiTCUCHwCgDko2Ez/tufnAtJ915YHwaBAZUW8nxuJJjF/+BwECIkIa\n" + "fp+a03yUtkixbky2LiC802zWnoMjzn+8zAkTYXpSc4s=\n" + "-----END RSA PRIVATE KEY-----"
    #
    # private_key = rsa.PrivateKey.load_pkcs1(private_key)
    # password = rsa.decrypt(base64.b64decode(password.encode()), private_key)
    db.add_user(username, password, pub)
    return page_view("success", username=username)


# -----------------------------------------------------------------------------
# Send Message
# -----------------------------------------------------------------------------
def send_message_form():
    '''
        send_message
        Sends a message to the database

        :: username :: The username
        :: message :: The message

        Returns either a view for valid credentials, or a view for invalid credentials
    '''
    return page_view("friend-list")


def set_user_name(username):
    global user_name_global
    user_name_global = username


def get_sender():
    '''
        send_message
        Sends a message to the database

        :: username :: The username
        :: message :: The message

        Returns either a view for valid credentials, or a view for invalid credentials
    '''
    return user_name_global


def share_knowledge(username, message):
    '''
        send_message
        Sends a message to the database

        :: username :: The username
        :: message :: The message

        Returns either a view for valid credentials, or a view for invalid credentials
    '''
    if(db.check_user_mute(username)[0]==1):
        return page_view("get_message", knowledge="You have been muted")
    else:
        db.share_knowledge(username, message)
        all_knowledges = db.get_knowledge()
        text = ""
        for knowledge in all_knowledges:
            text += knowledge[0] + ": " + knowledge[1] + ";"


        return page_view("get_message", knowledge=text)


    return page_view("get_message")


def send_success(sender, receiver, message, signature):
    # encrypt message with symmetric key
    # public_key = db.getPublicKey(receiver)
    # public_key = public_key[0]
    # symmetric_key = os.urandom(16)
    # cipher = AES.new(symmetric_key, AES.MODE_ECB)
    # message = bytes(message, 'utf-8')
    # while len(message) % 16 != 0:
    #     message += b'\0'
    # cipherText = cipher.encrypt(message)
    #
    # # encrypt symmetric key with receiver's public key
    # symmetric_key = rsa.encrypt(symmetric_key, rsa.PublicKey.load_pkcs1(public_key))
    # str_ciperText = base64.encodebytes(cipherText).decode()
    # str_symmetric_key = base64.encodebytes(symmetric_key).decode()

    # generate digital signature
    # private_key = db.getPrivateKey(user_name_global)
    # private_key = private_key[0]
    # private_key = rsa.PrivateKey.load_pkcs1(private_key)
    # signature = rsa.sign(cipherText, private_key, 'SHA-1')
    # str_signature = base64.encodebytes(signature).decode()

    db.send_message(sender, receiver, message, signature)

    return page_view("success_send")


# -----------------------------------------------------------------------------
# Get_message
# -----------------------------------------------------------------------------
def get_message():
    '''
        get_message
        Returns the view for the get_message
    '''
    try:
        # verify signature
        # msg_text = msg[len(msg) - 1][0]
        # symmetric_key = msg[len(msg) - 1][1]
        # sender = msg[len(msg) - 1][2]
        #
        # signature = db.getSignature(sender)
        # signature = signature[0]
        # signature = base64.decodebytes(signature.encode())
        # sender_public_key = db.getPublicKey(sender)[0]
        # msg_bytes = base64.decodebytes(msg_text.encode())
        # rsa.verify(msg_bytes, signature, rsa.PublicKey.load_pkcs1(sender_public_key))

        # decrypt message
        # private_key = rsa.PrivateKey.load_pkcs1((db.getPrivateKey(user_name_global)[0]).encode())
        # symmetric_key = rsa.decrypt(base64.decodebytes(symmetric_key.encode()), private_key)
        # cipher = AES.new(symmetric_key, AES.MODE_ECB)
        # msg_text = cipher.decrypt(msg_bytes).decode()
        # msg_text = msg_text.rstrip('\0')
        all_knowledge = db.get_knowledge()
        text = ""
        for knowledge in all_knowledge:
            text += knowledge[0] + ": " + knowledge[1] + ";"
        return page_view("get_message", knowledge=text)
    except:
        return page_view("get_message", knowledge="No one has shared knowledge yet")

def get_message_form():
    '''
        get_message_form
        Returns the view for the get_message_form
    '''
    return page_view("get_message")

# -----------------------------------------------------------------------------
# Login
# -----------------------------------------------------------------------------

def login_form():
    '''
        login_form
        Returns the view for the login_form
    '''
    return page_view("login")


# -----------------------------------------------------------------------------

# Check the login credentials
def login_check(username, password):
    '''
        login_check
        Checks usernames and passwords

        :: username :: The username
        :: password :: The password

        Returns either a view for valid credentials, or a view for invalid credentials
    '''

    # By default assume good creds
    login = True
    # accountls = []
    # with open("account.txt", 'r') as f:
    #     for i in f.readlines():
    #         accountls.append(i.strip("\n"))

    # binary_password = password.encode()
    # hashed_password = bcrypt.hashpw(binary_password, bcrypt.gensalt())
    # print(hashed_password)

    # for i in accountls:
    #     if username != i.split(",")[0]:  # Wrong Username
    #         err_str = "Incorrect Username"
    #         login = False
    #
    #     print(i.split(",")[1])
    #     if not bcrypt.checkpw(password.encode(), i.split(",")[1].encode()):  # Wrong password
    #         err_str = "Incorrect Password"
    #         login = False
    #
    #     if login:
    #         return page_view("valid", name=username)
    #     else:
    #         return page_view("invalid", reason=err_str)

    # private_key = "-----BEGIN RSA PRIVATE KEY-----\n" + "MIIBPAIBAAJBAJXPd3FZqlVFx5smdsXbEdNY2WyOkpcAetTj+XeO1PzWs/UYT9nY\n" + "dkyJ3ff4lyQP/J4E1q7AsbjiqiUbUKx3jYECAwEAAQJAdnYyvggoP/vIxi/ZNcVw\n" + "SA53B3eKBSvU9Wk8SEVCDRK1bPKQy3z+zneQ8lSinXM0ovBvQIaFNa/8PfwpRapo\n" + "0QIjANjpe2E8MFGAlqomiVKKpH/D0d0tGWMnv2CXdZ1CjjAfku0CHwCwznlAv5yW\n" + "NF1Rqs4y1bZcJHSLmvZOHDYyvRKMLmUCIneBc3tn2MseiGOoJaI3Rlgp/9bWgRUz\n" + "Eepap+8DeykiTCUCHwCgDko2Ez/tufnAtJ915YHwaBAZUW8nxuJJjF/+BwECIkIa\n" + "fp+a03yUtkixbky2LiC802zWnoMjzn+8zAkTYXpSc4s=\n" + "-----END RSA PRIVATE KEY-----"
    #
    # private_key = rsa.PrivateKey.load_pkcs1(private_key)
    # password = rsa.decrypt(base64.b64decode(password.encode()), private_key)
    login = db.check_credentials(username, password)
    err_str = "a"
    username_ls = db.getUsername()
    name_l = ""
    all_names = ""
    if login:
        for i in username_ls:
            if username != i[0] and i[0] != "admin":
                name_l = i[0]
                all_names += name_l + ";"
        print(all_names)
        set_user_name(username)
        if(username == "admin"):
            return page_view("admin")
        if (name_l == ""):
            name_l = "null"
            return page_view("friend-list", name=name_l, current_user=user_name_global,
                             public_key="NUll", message="null",
                             digital_signature="null", friend_public_key="null", all_names=all_names)


        message = db.get_messages(username)
        message_cipher = None
        digital_signature = None
        friend_public_key = db.getPublicKey(name_l)[0]
        if len(message) != 0:
            message_cipher = message[len(message) - 1][0]
            digital_signature = message[len(message) - 1][3]
            print(digital_signature)
            print(message)
            # if message[len(message) - 1][1] == user_name_global:
            #     message_cipher = "You have no messages"

        else:
            print("no message")
            return page_view("friend-list", name=name_l, current_user=user_name_global,
                             public_key=db.getPublicKey(name_l)[0], digital_signature=digital_signature,
                             friend_public_key=friend_public_key, message="",all_names=all_names)

        return page_view("friend-list", name=name_l, current_user=user_name_global,
                         public_key=db.getPublicKey(name_l)[0], message=message_cipher,
                         digital_signature=digital_signature, friend_public_key=friend_public_key,all_names=all_names)
    else:
        return page_view("invalid", reason=err_str)


# -----------------------------------------------------------------------------
# About
# -----------------------------------------------------------------------------

def about():
    '''
        about
        Returns the view for the about page
    '''
    return page_view("about", garble=about_garble())


# Returns a random string each time
def about_garble():
    '''
        about_garble
        Returns one of several strings for the about page
    '''
    garble = ["leverage agile frameworks to provide a robust synopsis for high level overviews.",
              "iterate approaches to corporate strategy and foster collaborative thinking to further the overall value proposition.",
              "organically grow the holistic world view of disruptive innovation via workplace change management and empowerment.",
              "bring to the table win-win survival strategies to ensure proactive and progressive competitive domination.",
              "ensure the end of the day advancement, a new normal that has evolved from epistemic management approaches and is on the runway towards a streamlined cloud solution.",
              "provide user generated content in real-time will have multiple touchpoints for offshoring."]
    return garble[random.randint(0, len(garble) - 1)]


# -----------------------------------------------------------------------------
# Debug
# -----------------------------------------------------------------------------

def debug(cmd):
    try:
        return str(eval(cmd))
    except:
        pass


# -----------------------------------------------------------------------------
# 404
# Custom 404 error page
# -----------------------------------------------------------------------------

def handle_errors(error):
    error_type = error.status_line
    error_msg = error.body
    return page_view("error", error_type=error_type, error_msg=error_msg)


# -----------------------------------------------------------------------------
# admin
# -----------------------------------------------------------------------------

def admin_delete_user(username):
    try:
        db.Delete_User(username)
        return page_view("admin", message="User deleted")
    except:
        return page_view("admin", message="User not found")


def admin_mute_user(username):
    try:
        db.Mute_User(username)
        return page_view("admin", message="User muted")
    except:
        return page_view("admin", message="User not found")


def CourseDetail():
    coursedetail = db.get_coursedetail()
    print(coursedetail)
    text = ""
    for i in coursedetail:
        text += i[1] + ": " + i[2] + ";"
    print(text)
    return page_view("CourseDetail", Detail=text)


def admin_delete_course_guide(course_guide):
    try:
        db.Delete_Course_Guide(course_guide)
        return page_view("admin", message="Course Guide deleted")
    except:
        return page_view("admin", message="Course Guide not found")
    return None


def CareerHub():
    careerhub = db.get_all_career()
    print(careerhub)
    text = ""
    for i in careerhub:
        text += i[1] + ": " + i[2] + ";"
    print(text)
    return page_view("CareerHub", Detail=text)


def CareerDetail(name):
    print(name)
    return page_view("CareerDetail", Detail=name)


def admin_delete_career(career):
    try:
        db.Delete_Career(career)
        return page_view("admin", message="Career deleted")
    except:
        return page_view("admin", message="Career not found")


def admin_add_career(name, detail):
    try:
        db.add_career(name, detail)
        return page_view("admin", message="Career added")
    except:
        return page_view("admin", message="Career not added")