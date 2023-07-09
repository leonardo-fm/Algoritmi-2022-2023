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
