# ProgramacaoLinear
Implementação em java do algoritmo simplex para resolução de problemas em programação linear.

Disciplina: Pesquisa Operacional.

Alunos: Gabriel Gonçalves, Paulo Ezequiel.

Passo a passo de como executar:
1) Adiciona os valores dos coenficientes da função objetiva no array 'Func'.

2) Adicionar os valores dos coefientes das funções de restrições na matriz 'Restrica'.
    o '<=' será representado por '1', '>=' representado '-1'.
    
Exemplo:
(MAX) Z = 40x1 + 30x2 
# sujeito a:
0,5x1 <= 8
x2 <= 8
x1+2x2<=24          


                    double[] func = { 40, 30 };
                    double[][] Restrica = { { 0.5, 0, 1, 8 }, { 0, 1, 1, 8 }, { 1, 2, 1, 24 } };
		
3) adicionar a quantidade de variáveis de decisão e quantidade de restrições no método 'montarMatriz' onde o 
   primeiro argumento é a quantidade de variáveis de decisão e o segundo a quantidade de restrições.
                                               



