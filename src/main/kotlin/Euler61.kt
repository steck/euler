import permutations.Permutations

class Euler61 : EulerTask {
    override fun solve(): Int {
        val t = (1..2000).map { it * (it + 1) / 2 }.dropWhile { it < 1000 }.takeWhile { it < 10000 }
        val s = (1..2000).map { it * it }.dropWhile { it < 1000 }.takeWhile { it < 10000 }
        val p = (1..2000).map { it * (3 * it - 1) / 2 }.dropWhile { it < 1000 }.takeWhile { it < 10000 }
        val hx = (1..2000).map { it * (2 * it - 1) }.dropWhile { it < 1000 }.takeWhile { it < 10000 }
        val hp = (1..2000).map { it * (5 * it - 3) / 2 }.dropWhile { it < 1000 }.takeWhile { it < 10000 }
        val oc = (1..2000).map { it * (3 * it - 2) }.dropWhile { it < 1000 }.takeWhile { it < 10000 }

        val map = hashMapOf(0 to t, 1 to s, 2 to p, 3 to hx, 4 to hp, 5 to oc)

        var res = 0
        for(perm in Permutations(6)){
            val extended_perm = perm.plus(perm[0])
            var test = Seq(map[0]!!.map { listOf(it) })
            var i = 0;
            while (i < 6){
                val a = map[extended_perm[i]]!!
                val b = map[extended_perm[i + 1]]!!
                test = applyNext(test, a, b)

                i++;
            }

            test = Seq(test.data.filter { it.first() == it.last() })

            if(test.data.isNotEmpty()){
                res = test.data[0].take(6).sum()
                println("collection: ${test}, res = ${res})")
            }
        }

      return res;
    }

    fun buildMap(a: List<Int>, b: List<Int>): Map<Int, List<Int>> {
        val res = HashMap<Int, List<Int>>();
        for (item in a) {
            val suffix = item % 100;
            res[item] = b.filter { it / 100 == suffix }
        }
        return res;
    }

    fun applyNext(seq: Seq, a: List<Int>, b: List<Int>): Seq {
        val map = buildMap(a, b);

        val res = ArrayList<List<Int>>()

        for (list in seq.data) {
            val last = list.last()
            for (item in map[last]?: emptyList()){
                res.add(list.plus(item))
            }
        }

        return Seq(res)
    }
}

data class Seq(val data: List<List<Int>>)