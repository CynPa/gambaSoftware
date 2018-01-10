/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ProcesoImportacion;
/*  4:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Map;
/*  8:   */ import javax.ejb.Stateless;
/*  9:   */ 
/* 10:   */ @Stateless
/* 11:   */ public class ProcesoImportacionDao
/* 12:   */   extends AbstractDaoAS2<ProcesoImportacion>
/* 13:   */ {
/* 14:   */   public ProcesoImportacionDao()
/* 15:   */   {
/* 16:36 */     super(ProcesoImportacion.class);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ProcesoImportacion buscarPorCodigo(String codigo)
/* 20:   */     throws ExcepcionAS2
/* 21:   */   {
/* 22:48 */     Map<String, String> filters = new HashMap();
/* 23:49 */     filters.put("codigo", codigo);
/* 24:   */     
/* 25:51 */     List<ProcesoImportacion> lista = obtenerListaCombo("codigo", true, filters);
/* 26:53 */     if (lista.isEmpty()) {
/* 27:54 */       throw new ExcepcionAS2("msg_no_hay_datos", " ProcesoImportacion codigo=" + codigo);
/* 28:   */     }
/* 29:56 */     return (ProcesoImportacion)lista.get(0);
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ProcesoImportacionDao
 * JD-Core Version:    0.7.0.1
 */