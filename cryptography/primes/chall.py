from Crypto.Util.number import getPrime, bytes_to_long
import random

flag = b"NCLPS1{REDACTED}"
flag = bytes_to_long(flag)

primes = []
while True:
    if len(primes) == 128:
        break
    p = getPrime(128)
    if p not in primes:
        primes.append(p)

e = 0x10001
while True:
    n = 1
    for p in primes:
        r = random.randint(0, 1)
        if r:
            n *= p
    if n.bit_length() > 128:
        break

ct = pow(flag, e, n)
with open("output.txt", 'w') as f:
    f.write(f"p = {[p for p in primes if p in primes]}\n")
    f.write(f"c = {ct}\n")
    f.close()