import zipfile

def crack_password(password_list, obj):
    # tracking line no. at which password is found
    idx = 0

    # open file in read byte mode only as "rockyou.txt"
    # file contains some special characters and hence
    # UnicodeDecodeError will be generated
    with open(password_list, 'rb') as file:
        for line in file:
            for word in line.split():
                try:
                    idx += 1
                    obj.extractall(pwd=word)
                    print("Password found at line", idx)
                    print("Password is", word.decode())
                    return True
                except:
                    continue
    return False





# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    print('Hi Andrei')

    password_list = "rockyou.txt"

    zip_file = "subiecte6iunie.zip"

    # ZipFile object initialised
    obj = zipfile.ZipFile(zip_file)

    # count of number of words present in file
    cnt = len(list(open(password_list, "rb")))

    print("There are total", cnt,
          "number of passwords to test")

    if crack_password(password_list, obj) == False:
        print("Password not found in this file")

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
