/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Pais;
/*  4:   */ import com.asinfo.as2.entities.Provincia;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class PaisDao
/* 12:   */   extends AbstractDaoAS2<Pais>
/* 13:   */ {
/* 14:   */   public PaisDao()
/* 15:   */   {
/* 16:37 */     super(Pais.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Pais cargarDetalle(int id)
/* 20:   */   {
/* 21:48 */     Pais pais = (Pais)buscarPorId(Integer.valueOf(id));
/* 22:   */     
/* 23:   */ 
/* 24:51 */     pais.getListaProvincia().size();
/* 25:53 */     for (Provincia provincia : pais.getListaProvincia()) {
/* 26:54 */       provincia.getListaCiudad().size();
/* 27:   */     }
/* 28:57 */     return pais;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Pais buscarPorNombre(String nombre)
/* 32:   */   {
/* 33:61 */     Map<String, String> filters = new HashMap();
/* 34:62 */     filters.put("nombre", nombre);
/* 35:   */     
/* 36:64 */     List<Pais> listaPais = obtenerListaCombo("nombre", false, filters);
/* 37:66 */     if (listaPais.size() > 0) {
/* 38:67 */       return (Pais)listaPais.get(0);
/* 39:   */     }
/* 40:69 */     return null;
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.PaisDao
 * JD-Core Version:    0.7.0.1
 */