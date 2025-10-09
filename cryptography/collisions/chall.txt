s = "6708225246577317268201359870589947128321270453156893234886662491398921333524854484228506876784333764380835907785971649995603806504942718660141508646410278513251598900"
y = int(s)
shifts = [1,2,4,8,16,32,64,128,256]
wordlen = y.bit_length()

def invert_shift_right(y, s, wordlen):
    x = 0
    for i in range(wordlen-1, -1, -1):
        ybit = (y >> i) & 1
        if i + s >= wordlen:
            xbit = ybit
        else:
            xbit = ybit ^ ((x >> (i + s)) & 1)
        x |= (xbit << i)
    return x

def invert_sequence(y, shifts, wordlen):
    x = y
    for s in reversed(shifts):
        x = invert_shift_right(x, s, wordlen)
    return x

x = invert_sequence(y, shifts, wordlen)
b = x.to_bytes((x.bit_length()+7)//8, 'big')
print(b.decode('utf-8'))
