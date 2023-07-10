report <- read.csv("C:\\Users\\Lo\\Desktop\\Cose mie\\Università\\Informatica\\2023 Q3\\Algoritmi\\laboratorio-algoritmi-2022-2023\\ex1\\report\\report_tests.csv")
normalize_vector <- function(input_vector) {
  min_value <- min(input_vector)
  max_value <- max(input_vector)
  normalized_vector <- (input_vector - min_value) / (max_value - min_value)
  return(normalized_vector)
}

report$col_index <- factor(report$col_index)
levels(report$col_index) <- c("string", "integer", "float")
report$nline <- factor(report$nline)
levels(report$nline) <- c("low", "medium", "high")

# First plot
report_h <- report[report$nline == "high", ]
report_h$time <- normalize_vector(report_h$time)
report_m <- report[report$nline == "medium", ]
report_m$time <- normalize_vector(report_m$time)
report_l <- report[report$nline == "low", ]
report_l$time <- normalize_vector(report_l$time)
plot(report_h$k, report_h$time, pch = 15, xaxt="n", col = report$col_index, ylab = "Normalized time (2.671S to 24.057S)", xlab = "k")
axis(1, at = seq(0, 30, by = 1), las = 2)
legend(x = "topleft", bty = "n", cex = 1, horiz = TRUE, legend = levels(report$col_index), fill = c(1, 2, 3))
legend(x = "bottomright", bty = "n", cex = 0.8, horiz = TRUE, legend = c("20M", "10M", "5M"), pch = c(15, 16, 17))
points(report_m$k, report_m$time, pch = 16, col = report$col_index)
points(report_l$k, report_l$time, pch = 17, col = report$col_index)

# Second plot
report <- read.csv("C:\\Users\\Lo\\Desktop\\Cose mie\\Università\\Informatica\\2023 Q3\\Algoritmi\\laboratorio-algoritmi-2022-2023\\ex1\\report\\report_tests.csv")
report$nline <- factor(report$nline)
levels(report$nline) <- c("low", "medium", "high")
report$col_index <- factor(report$col_index)
levels(report$col_index) <- c("string", "integer", "float")

report_h <- report[report$nline == "high", ]
report_m <- report[report$nline == "medium", ]
report_l <- report[report$nline == "low", ]

m1 <- c(
        mean(report_l$time[report_l$col_index == "integer"]),
        mean(report_m$time[report_m$col_index == "integer"]),
        mean(report_h$time[report_h$col_index == "integer"])
)
m2 <- c(
        mean(report_l$time[report_l$col_index == "float"]),
        mean(report_m$time[report_m$col_index == "float"]),
        mean(report_h$time[report_h$col_index == "float"])
)
m3 <- c(
        mean(report_l$time[report_l$col_index == "string"]),
        mean(report_m$time[report_m$col_index == "string"]),
        mean(report_h$time[report_h$col_index == "string"])
)

nitem <- c(5, 10, 20)
x <- seq(0, 20, length.out = 100)
logF <- log(x);
FlogF <- x*log(x);
plot(1, type = "n", xlim = c(5, 20), ylim = c(0, 30), ylab = "Time (s)", xlab = "nitems (milion)")
abline(0, 1, col = "blue", lty = 2) #N
lines(x, logF, col = "orange", lty = 2) #log N
lines(x, FlogF, col = "violet", lty = 2) #N log N

lines(nitem, m3, pch = 19, col = 1)
lines(nitem, m2, pch = 19, col = 2)
lines(nitem, m1, pch = 19, col = 3)

values <- c("string", "integer", "float", "N", "Log N", "N Log N");
colors <- c("black", "red", "green", "blue", "orange", "violet")
legend(x = "topleft", bty = "n", cex = .8, legend = values, fill = colors)

