input = [row.split('   ') for row in open('input.txt').read().split('\n') if len(row) > 1]
list_1, list_2 = zip(*input)

# Part 1:
zip(sorted(list_1), sorted(list_2))
distance = sum(abs(int(i) - int(j)) for i, j in zip(sorted(list_1), sorted(list_2)))

print(distance)

# Part 2:
occurences = {}
for i in list_2:
    occurences[int(i)] = occurences.get(int(i), 0) + 1

similarity_score = sum(int(i) * occurences[int(i)] for i in list_1 if int(i) in occurences.keys())

print(similarity_score)


