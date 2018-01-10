/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Configuracion;
/*   4:    */ import com.asinfo.as2.entities.TipoAsiento;
/*   5:    */ import com.asinfo.as2.enumeraciones.Parametro;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.ejb.Stateless;
/*  10:    */ import javax.persistence.EntityManager;
/*  11:    */ import javax.persistence.EntityManagerFactory;
/*  12:    */ import javax.persistence.Query;
/*  13:    */ 
/*  14:    */ @Stateless
/*  15:    */ public class ConfiguracionDao
/*  16:    */   extends AbstractDaoAS2<Configuracion>
/*  17:    */ {
/*  18:    */   public ConfiguracionDao()
/*  19:    */   {
/*  20: 34 */     super(Configuracion.class);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void actualizarTipoAsiento(TipoAsiento tipoAsiento, int idOrganizacion)
/*  24:    */   {
/*  25: 44 */     StringBuilder sql = new StringBuilder();
/*  26: 45 */     sql.append("UPDATE Configuracion c ");
/*  27: 46 */     sql.append(" SET c.valor = :idTipoAsiento ");
/*  28: 47 */     sql.append(" WHERE c.idOrganizacion = :idOrganizacion ");
/*  29: 48 */     sql.append(" AND c.valorMostrar = :nombreTipoAsiento ");
/*  30:    */     
/*  31: 50 */     Query query = this.em.createQuery(sql.toString());
/*  32: 51 */     query.setParameter("idTipoAsiento", String.valueOf(tipoAsiento.getIdTipoAsiento()));
/*  33: 52 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  34: 53 */     query.setParameter("nombreTipoAsiento", tipoAsiento.getNombre());
/*  35:    */     
/*  36: 55 */     query.executeUpdate();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<Configuracion> obtenerListaConfiguracionPorParametro(Parametro parametro)
/*  40:    */   {
/*  41: 60 */     StringBuilder sql = new StringBuilder();
/*  42: 61 */     sql.append(" SELECT conf FROM Configuracion conf");
/*  43: 62 */     sql.append(" WHERE conf.parametro = :parametro");
/*  44:    */     
/*  45: 64 */     Query query = this.em.createQuery(sql.toString());
/*  46: 65 */     query.setParameter("parametro", parametro);
/*  47: 66 */     return query.getResultList();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List<Configuracion> listaConfiguracionPorModulo(String nombre, int idOrganizacion)
/*  51:    */   {
/*  52: 72 */     StringBuilder sql = new StringBuilder();
/*  53: 73 */     sql.append(" SELECT conf FROM Configuracion conf");
/*  54: 74 */     sql.append(" LEFT JOIN conf.modulo mo ");
/*  55: 75 */     sql.append(" WHERE mo.nombre = :nombre");
/*  56: 76 */     sql.append(" AND conf.idOrganizacion = :idOrganizacion ");
/*  57: 77 */     sql.append(" ORDER BY conf.parametro");
/*  58: 78 */     Query query = this.em.createQuery(sql.toString());
/*  59: 79 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/*  60: 80 */     query.setParameter("nombre", nombre);
/*  61: 81 */     return query.getResultList();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Map<String, Object> obtenerProperties()
/*  65:    */   {
/*  66: 86 */     return this.em.getEntityManagerFactory().getProperties();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Query ejecutarNativeQuery(String sqlString)
/*  70:    */   {
/*  71: 90 */     return this.em.createNativeQuery(sqlString);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void execute(String sql)
/*  75:    */     throws SQLException
/*  76:    */   {
/*  77: 94 */     Query q = this.em.createNativeQuery(sql);
/*  78: 95 */     q.executeUpdate();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String obtenerPropiedadesPersistence()
/*  82:    */   {
/*  83:100 */     String dialect = "hibernate.dialect";
/*  84:    */     
/*  85:    */ 
/*  86:103 */     Map<String, Object> propertiesMap = obtenerProperties();
/*  87:104 */     Object value = propertiesMap.get(dialect);
/*  88:    */     String databaseDialectName;
/*  89:106 */     if (value != null) {
/*  90:107 */       databaseDialectName = (String)value;
/*  91:    */     } else {
/*  92:109 */       throw new RuntimeException(String.format("Property `%s' not found", new Object[] { dialect }));
/*  93:    */     }
/*  94:    */     String databaseDialectName;
/*  95:111 */     return databaseDialectName;
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ConfiguracionDao
 * JD-Core Version:    0.7.0.1
 */