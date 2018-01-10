/*  1:   */ package com.asinfo.as2.dao.seguridad;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.dao.GenericoDao;
/*  5:   */ import com.asinfo.as2.entities.VersionSistema;
/*  6:   */ import com.asinfo.as2.entities.seguridad.EntidadSistema;
/*  7:   */ import java.util.HashMap;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.ejb.Stateless;
/* 12:   */ import javax.persistence.EntityManager;
/* 13:   */ import javax.persistence.NoResultException;
/* 14:   */ import javax.persistence.Query;
/* 15:   */ 
/* 16:   */ @Stateless
/* 17:   */ public class SistemaDao
/* 18:   */   extends AbstractDaoAS2<EntidadSistema>
/* 19:   */ {
/* 20:   */   @EJB
/* 21:   */   protected transient GenericoDao<VersionSistema> versionSistemaDao;
/* 22:   */   
/* 23:   */   public SistemaDao()
/* 24:   */   {
/* 25:44 */     super(EntidadSistema.class);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public EntidadSistema buscarPorNombre(String nombre)
/* 29:   */   {
/* 30:54 */     String sql = "SELECT s FROM EntidadSistema s WHERE s.nombre=:nombre";
/* 31:55 */     Query query = this.em.createQuery(sql);
/* 32:56 */     query.setParameter("nombre", nombre);
/* 33:57 */     query.setMaxResults(1);
/* 34:   */     try
/* 35:   */     {
/* 36:60 */       return (EntidadSistema)query.getSingleResult();
/* 37:   */     }
/* 38:   */     catch (NoResultException e) {}
/* 39:62 */     return null;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String obtenerVersionSistema()
/* 43:   */   {
/* 44:67 */     String versionActual = null;
/* 45:68 */     Map<String, String> filters = new HashMap();
/* 46:69 */     filters.put("sistema.nombre", "AS2-ERP".toString());
/* 47:70 */     List<VersionSistema> listaVersionSistema = this.versionSistemaDao.obtenerListaCombo(VersionSistema.class, "numero", true, filters);
/* 48:71 */     if (listaVersionSistema.size() > 0) {
/* 49:72 */       versionActual = ((VersionSistema)listaVersionSistema.get(0)).getNumero().trim();
/* 50:   */     }
/* 51:74 */     return versionActual;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.seguridad.SistemaDao
 * JD-Core Version:    0.7.0.1
 */