package br.com.backendilegraav.backendilegraav.vendas.writer.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import br.com.backendilegraav.backendilegraav.vendas.dominio.Cliente;


public class vendasItemWriterListener implements ItemWriteListener<Object> {

	
	@Override
	public void beforeWrite(List<? extends Object> items) {
		System.out.println(" ## beforeWrite " + items );
		if(items instanceof Cliente) {
			
		}
	}

	@Override
	public void afterWrite(List<? extends Object> items) {
		System.out.println(" ## afterWrite ");
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Object> items) {
		// TODO Auto-generated method stub

	}

}
