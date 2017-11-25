# Parallel Stream
Por default, o Stream do Java é serial, ou seja, as tasks são executadas em sequência na mesma Thread.  
O Stream se torna paralelo quando expecificamos com, por exemplo: 
```
AlgumaCollection().parallelStream()
```
O ParallelStream quebra o Stream em sub tarefas, cada uma executada numa Thread em separado, e depois os resultados são agregados e retornados de forma bem simples.  
O ParallelStream pode ser até mais pesado que o Stream default ja que é necessário o gerenciamento e coordenação das múltiplas Threads e o mesmo deve ser utilizado apenas em 
casos muito específicos.

