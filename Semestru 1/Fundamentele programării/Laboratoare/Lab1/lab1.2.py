l = [1, 2, 3, 4, 5, 6]
'''

for i in range(0, 6):
    print(l[i])

for i in l:
    print("Elementele listei sunt: ", i)
while len(l)>0:
    a = l.pop()
    if a<3:
        print("Bad")
    elif a==4:
        print("Good")
    else:
        print("Nice")
    print (a)
    print(l)
'''
s = ""
'''
for ...
    s = s +...
print (s)
'''
for i in l:
    s+=" " + str(i)

print (s)
