/*  1:   */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.TituloDao;
/*  4:   */ import com.asinfo.as2.entities.Titulo;
/*  5:   */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTitulo;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.EJB;
/*  9:   */ import javax.ejb.Stateless;
/* 10:   */ 
/* 11:   */ @Stateless
/* 12:   */ public class ServicioTituloImpl
/* 13:   */   implements ServicioTitulo
/* 14:   */ {
/* 15:   */   @EJB
/* 16:   */   private TituloDao tituloDao;
/* 17:   */   
/* 18:   */   public void guardar(Titulo titulo)
/* 19:   */   {
/* 20:40 */     this.tituloDao.guardar(titulo);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void eliminar(Titulo titulo)
/* 24:   */   {
/* 25:49 */     this.tituloDao.eliminar(titulo);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Titulo buscarPorId(int idTitulo)
/* 29:   */   {
/* 30:58 */     return (Titulo)this.tituloDao.buscarPorId(Integer.valueOf(idTitulo));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<Titulo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 34:   */   {
/* 35:66 */     return this.tituloDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public List<Titulo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 39:   */   {
/* 40:74 */     return this.tituloDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int contarPorCriterio(Map<String, String> filters)
/* 44:   */   {
/* 45:82 */     return this.tituloDao.contarPorCriterio(filters);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Titulo cargarDetalle(int idTitulo)
/* 49:   */   {
/* 50:91 */     return null;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public Titulo buscarPorNombre(String nombre)
/* 54:   */   {
/* 55:99 */     return this.tituloDao.buscarPorNombre(nombre);
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioTituloImpl
 * JD-Core Version:    0.7.0.1
 */