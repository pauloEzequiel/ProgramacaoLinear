package programacaoLinear;

public class Simplex {
	private static double[][] tabela;
	private static int linha;
	private static int coluna;

	public static void main(String[] args) {

		// double[][] Restrica={{2,1,1,6},{10,12,1,60}};
		// double[]func={5,2};

		 //double[][] Restrica={{2,4,1,100},{3,2,1,90},{5,3,1,120}};
		 //double[]func={120,150};

		double[][] Restrica = { { 0.5, 0, 1, 8 }, { 0, 1, 1, 8 }, { 1, 2, 1, 24 } };
		double[] func = { 40, 30 };

		try {

			montarMatriz(2, 3, Restrica, func);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(
					"POR FAVOR, VERIFICAR OS VALORES INFORMADOS.EXISTE INCONSISTÊNCIA NOS ITENS INFORMADOS!!!");
		}

		System.out.println();

		CalcularSimplex();
	}

	public static double[][] montarMatriz(int NumVarDecisao, int numRestricao, double[][] MatrizRestricao,
			double[] FObjetiva) {
		linha = numRestricao + 1;
		coluna = NumVarDecisao + numRestricao + 1;
		tabela = new double[linha][coluna];

		// Preencher tabela com Zero
		for (int i = 0; i < linha; i++) {
			for (int j = 0; j < coluna; j++)
				tabela[i][j] = 0;
		}

		// Preencher última linha com valores da função objetiva

		for (int i = 0; i < NumVarDecisao; i++)
			tabela[numRestricao][i] = FObjetiva[i] * (-1);

		for (int i = 0; i < numRestricao; i++) {
			tabela[i][coluna - 1] = MatrizRestricao[i][NumVarDecisao + 1]; // preencher
																			// ultima
																			// coluna
																			// com
																			// os
																			// valores
																			// da
																			// última
																			// coluna
																			// da
																			// MatrizRestrição
			for (int j = 0; j < coluna - 1; j++) {
				if (j == NumVarDecisao)
					tabela[i][j + i] = MatrizRestricao[i][j]; // Insere os
																// coeficientes
																// das variaveis
																// de folgas
				else if (j < numRestricao)
					tabela[i][j] = MatrizRestricao[i][j]; // Insere os
															// coeficientes das
															// variaveis de
															// decisão
			}
		}

		for (int i = 0; i < linha; i++) {
			for (int j = 0; j < coluna; j++) {
				System.out.print(tabela[i][j] + " ");
			}
			System.out.println();
		}

		return tabela;
	}

	public static void CalcularSimplex() {
		int c, l, cont = 0;
		while (VerificarSeOtimo() == false && cont < linha) {
			c = EncontraColunaPivo();
			l = EncontraLinhaPivo(c);
			Calcular(l, c);
			cont++;
		}
		for (int i = 0; i < linha; i++) {
			for (int j = 0; j < coluna; j++) {
				System.out.print(tabela[i][j] + " ");
			}
			System.out.println();
		}
		ExibirResposta();
	}

	public static boolean VerificarSeOtimo() {
		boolean Otimo = false;
		int Count = 0;
		for (int i = 0; i < coluna; i++) {
			double val = tabela[linha - 1][i];
			if (val >= 0) {
				Count++;
			}
		}

		if (Count == coluna) {
			Otimo = true;
		}

		return Otimo;
	}

	public static double[][] Calcular(int LinhaPivo, int ColunaPivo) {
		double ValorPivo = tabela[LinhaPivo][ColunaPivo];

		for (int i = 0; i < coluna; i++) {
			tabela[LinhaPivo][i] /= ValorPivo;
		}
		for (int i = 0; i < linha; i++) {
			if (i != LinhaPivo) {
				double c = tabela[i][ColunaPivo];
				for (int j = 0; j < coluna; j++) {
					tabela[i][j] = tabela[i][j] - (c * (tabela[LinhaPivo][j]));
				}

			}
		}

		return tabela;
	}

	private static int EncontraColunaPivo() {
		double[] valores = new double[coluna];
		int local = 0;

		int pos, count = 0;
		for (pos = 0; pos < coluna - 1; pos++) {
			if (tabela[linha - 1][pos] < 0) {
				// System.out.println("Valor Negativo Encontrado");
				count++;
			}
		}

		if (count > 1) {
			for (int i = 0; i < coluna - 1; i++)
				valores[i] = Math.abs(tabela[linha - 1][i]);
			local = EncontrarMaiorValor(valores);
		} else
			local = count - 1;

		return local;
	}

	private static int EncontrarMenorValor(double[] data) {
		double minimum;
		int c, location = 0;
		minimum = data[0];

		for (c = 1; c < data.length; c++) {
			if (data[c] > 0) {
				if (Double.compare(data[c], minimum) < 0) {
					minimum = data[c];
					location = c;
				}
			}
		}

		return location;
	}

	private static int EncontraLinhaPivo(int colunaPivo) {
		double[] positivosVal = new double[linha];
		double[] res = new double[linha];
		int CountValNegative = 0;
		for (int i = 0; i < linha; i++) {
			if (tabela[i][colunaPivo] > 0) {
				positivosVal[i] = tabela[i][colunaPivo];
			} else {
				positivosVal[i] = 0;
				CountValNegative++;
			}

		}

		if (CountValNegative == linha)
			System.out.println("HÁ INFINITAS SOLUÇÕES");
		else {
			for (int i = 0; i < linha; i++) {
				double val = positivosVal[i];
				if (val > 0) {
					res[i] = tabela[i][coluna - 1] / val;
				}
			}
		}

		return EncontrarMenorValor(res);
	}

	private static int EncontrarMaiorValor(double[] data) {
		double maximum = 0;
		int c, location = 0;
		maximum = data[0];

		for (c = 1; c < data.length; c++) {
			if (Double.compare(data[c], maximum) > 0) {
				maximum = data[c];
				location = c;
			}
		}

		return location;
	}

	private static void ExibirResposta() {
		int pos = -1, cont = 0;

		System.out.printf("Z = %.3f\n", tabela[linha - 1][coluna - 1]);

		for (int i = 0; i < coluna - linha; i++) {
			for (int j = 0; j < linha; j++) {
				if (tabela[j][i] == 1)
					pos = j;
				else
					cont++;
			}
			if (cont == linha)
				System.out.printf("X%d=0\n", i + 1);
			else if (pos > -1)
				System.out.printf("X%d = %.3f\n", i + 1, tabela[pos][coluna - 1]);
			pos = -1;
		}
	}

}
