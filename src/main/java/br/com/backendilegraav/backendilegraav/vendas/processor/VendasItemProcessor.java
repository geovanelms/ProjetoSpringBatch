package br.com.backendilegraav.backendilegraav.vendas.processor;

import org.springframework.batch.item.ItemProcessor;

public class VendasItemProcessor<T> implements ItemProcessor<T, T> {

	@Override
	public T process(T item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}
