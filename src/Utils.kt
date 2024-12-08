import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
  .toString(16)
  .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Executes the given [block] and returns elapsed time in milliseconds.
 * https://proandroiddev.com/measuring-execution-times-in-kotlin-460a0285e5ea
 */

inline fun <T> measureTimeMillis(
  loggingFunction: (Long, T) -> Unit,
  function: () -> T
): T {

  val startTime = System.currentTimeMillis()
  val result: T = function.invoke()
  loggingFunction.invoke(System.currentTimeMillis() - startTime, result)

  return result
}

fun splitToListsOfInts(separator: Char, input: List<String>): List<List<Int>> {
  return input.filter { it.contains(separator) }
    .map { line -> line.split(separator).map { it.toInt() } }
}

/**
 * GCD by Euclidean algorithm
 */
fun gcd(x: Long, y: Long): Long {
  return if (y == 0L) x else gcd(y, x % y)
}

/**
 * LCM - least common multiple
 * |(a*b)|/gcd(a,b)
 */
fun lcm(x: Long, y: Long): Long {
  return abs(x * y) / gcd(x, y);
}