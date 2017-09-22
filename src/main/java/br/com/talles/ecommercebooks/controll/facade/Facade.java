package br.com.talles.ecommercebooks.controll.facade;

import br.com.talles.ecommercebooks.business.IStrategy;
import br.com.talles.ecommercebooks.controll.Result;
import br.com.talles.ecommercebooks.domain.Book;
import br.com.talles.ecommercebooks.domain.Entity;
import br.com.talles.ecommercebooks.persistence.dao.BookDao;
import br.com.talles.ecommercebooks.persistence.dao.IDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Facade implements IFacade {

	private Map<String, Map<String, List<IStrategy>>> requirements;
    private Map<String, IDao> persistence;
    private Result result;
    
    private static final String SELECT = "LIST";
    private static final String SAVE = "SAVE";
    private static final String DELETE = "DELETE";
    private static final String FIND = "FIND";
    private static final String UPDATE = "UPDATE";
	private static final String CREATE = "CREATE";
	private static final String FILTERS = "FILTERS";
	
	 public Facade() {
        String book = Book.class.getSimpleName();
        
        // All Strategies
        // ...
                
        List<IStrategy> saveBook = new ArrayList();
		
		
		List<IStrategy> deleteBook = new ArrayList();
		
        
        List<IStrategy> updateBook = new ArrayList();
		
		
		List<IStrategy> createBook = new ArrayList();
		
		
		List<IStrategy> filtersBook = new ArrayList();
        
		
        Map<String, List<IStrategy>> contextBook = new HashMap();
        contextBook.put(SAVE, saveBook);
		contextBook.put(DELETE, deleteBook);
        contextBook.put(UPDATE, updateBook);
        contextBook.put(CREATE, createBook);
        contextBook.put(FILTERS, filtersBook);
		
        requirements = new HashMap();
        requirements.put(book, contextBook);
        
        persistence = new HashMap();
        persistence.put(book, new BookDao());

        this.result = new Result();
    }
	 
	@Override
	public Result list(Entity entity) {
        this.result = new Result();
		
		Map<String, List<IStrategy>> reqs = requirements.get(entity.getClass().getName());
        List<IStrategy> validations = reqs.get(SELECT);
		
		result = executeValidations(entity, validations);
        if(result.hasMsg())
            return result;
		
		IDao dao = persistence.get(entity.getClass().getName());
        result.setEntities(dao.select());
        
        return result;
	}
	
	@Override
	public Result save(Entity entity) {
        this.result = new Result();
		
		Map<String, List<IStrategy>> reqs = requirements.get(entity.getClass().getName());
        List<IStrategy> validations = reqs.get(SAVE);
		
        result = executeValidations(entity, validations);
        if(result.hasMsg())
            return result;
		
        IDao dao = persistence.get(entity.getClass().getName());
        boolean resultDao = dao.save(entity);
        
        if(!resultDao)
            result.addMsg("An error has occurred in the process of your operation, "
					+ "it has been noted and will be resolved soon!");
        
        return result;
	}

	@Override
	public Result delete(Entity entity) {
		this.result = new Result();
		
		Map<String, List<IStrategy>> reqs = requirements.get(entity.getClass().getName());
        List<IStrategy> validations = reqs.get(DELETE);
		
		result = executeValidations(entity, validations);
        if(result.hasMsg())
            return result;
		
		IDao dao = persistence.get(entity.getClass().getName());        
        boolean resultDao = dao.delete(entity);
		
        if(!resultDao)
            result.addMsg("An error has occurred in the process of your operation, "
					+ "it has been noted and will be resolved soon!");
        
        return result;
	}
	
	@Override
	public Result find(Entity entity) {
		this.result = new Result();
		
        Map<String, List<IStrategy>> reqs = requirements.get(entity.getClass().getName());
        List<IStrategy> validations = reqs.get(FIND);
		
		result = executeValidations(entity, validations);
        if(result.hasMsg())
            return result;
				
	    IDao dao = persistence.get(entity.getClass().getName());
	    result.setEntity(dao.find(entity));
		
	    return result;
	}
	
	@Override
    public Result update(Entity entity) {
        this.result = new Result();
        
		Map<String, List<IStrategy>> reqs = requirements.get(entity.getClass().getName());
        List<IStrategy> validations = reqs.get(UPDATE);
		
        //result.setEntity(entity);
		result = executeValidations(entity, validations);
        if(result.hasMsg())
            return result;

        IDao dao = persistence.get(entity.getClass().getName());
        boolean resultDao = dao.update(entity);
        if(!resultDao)
            result.addMsg("An error has occurred in the process of your operation, "
					+ "it has been noted and will be resolved soon!");

        return result;
    }
	
	@Override
	public Result create(Entity entity){
		this.result = new Result();
		
		// TODO: Aqui vem meu lindo código c:
		
		return result;
	}
	
	public Result executeValidations(Entity entity, List<IStrategy> validations) {
        
        for(IStrategy validation : validations){
			// TODO: USAR ESTE CÓDIGO NAS STRATEGIES!!!
            //result.addMsg(validation.process(entity));
            if(result.hasMsg()){
                result.setEntity(entity);
                return result;
            }
        }
        
        return result;
    }
	
}