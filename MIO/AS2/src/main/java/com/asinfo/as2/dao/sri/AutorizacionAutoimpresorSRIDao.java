/*   1:    */ package com.asinfo.as2.dao.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*   4:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*   5:    */ import com.asinfo.as2.entities.Sucursal;
/*   6:    */ import com.asinfo.as2.entities.sri.AutorizacionAutoimpresorSRI;
/*   7:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*   8:    */ import com.asinfo.as2.entities.sri.AutorizacionPuntoDeVentaAutoimpresorSRI;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.enumeraciones.ProcesoAutoimpresorSRIEnum;
/*  11:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ import javax.persistence.EntityManager;
/*  16:    */ import javax.persistence.NoResultException;
/*  17:    */ import javax.persistence.Query;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class AutorizacionAutoimpresorSRIDao
/*  22:    */   extends AbstractDaoAS2<AutorizacionAutoimpresorSRI>
/*  23:    */ {
/*  24:    */   public AutorizacionAutoimpresorSRIDao()
/*  25:    */   {
/*  26: 38 */     super(AutorizacionAutoimpresorSRI.class);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public AutorizacionAutoimpresorSRI cargarDetalle(int idAutorizacionAutoimpresorSRI)
/*  30:    */   {
/*  31: 42 */     AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI = (AutorizacionAutoimpresorSRI)buscarPorId(Integer.valueOf(idAutorizacionAutoimpresorSRI));
/*  32: 43 */     autorizacionAutoimpresorSRI.getListaAutorizacionDocumentoAutoimpresorSRI().size();
/*  33: 44 */     for (AutorizacionPuntoDeVentaAutoimpresorSRI a : autorizacionAutoimpresorSRI.getListaAutorizacionPuntoDeVentaAutoimpresorSRI())
/*  34:    */     {
/*  35: 45 */       a.getPuntoDeVenta().getId();
/*  36: 46 */       a.getPuntoDeVenta().getSucursal().getId();
/*  37:    */     }
/*  38: 48 */     for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI a : autorizacionAutoimpresorSRI
/*  39: 49 */       .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRI())
/*  40:    */     {
/*  41: 50 */       a.getPuntoDeVenta().getId();
/*  42: 51 */       a.getPuntoDeVenta().getSucursal().getId();
/*  43:    */     }
/*  44: 53 */     if (autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior() != null) {
/*  45: 54 */       autorizacionAutoimpresorSRI.getAutorizacionAutoimpresorSRIAnterior().getId();
/*  46:    */     }
/*  47: 56 */     return autorizacionAutoimpresorSRI;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI obtenerSecuencia(DocumentoBase documentoBase, PuntoDeVenta puntoDeVenta)
/*  51:    */     throws ExcepcionAS2Financiero
/*  52:    */   {
/*  53: 63 */     StringBuilder sql = new StringBuilder();
/*  54: 64 */     sql.append(" SELECT adp ");
/*  55: 65 */     sql.append(" FROM AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adp ");
/*  56: 66 */     sql.append(" JOIN FETCH adp.autorizacionAutoimpresorSRI a");
/*  57: 67 */     sql.append(" JOIN FETCH adp.puntoDeVenta pv");
/*  58: 68 */     sql.append(" WHERE adp.documentoBase = :documentoBase AND pv = :puntoDeVenta and a.activo = true");
/*  59: 69 */     Query query = this.em.createQuery(sql.toString());
/*  60:    */     
/*  61: 71 */     query.setParameter("documentoBase", documentoBase);
/*  62: 72 */     query.setParameter("puntoDeVenta", puntoDeVenta);
/*  63:    */     try
/*  64:    */     {
/*  65: 76 */       return (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI)query.getSingleResult();
/*  66:    */     }
/*  67:    */     catch (NoResultException e) {}
/*  68: 87 */     return null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<Date> obtenerFechaProceso(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum procesoAutoimpresorSRI)
/*  72:    */   {
/*  73: 95 */     StringBuilder sql = new StringBuilder();
/*  74: 96 */     String fecha = "";
/*  75: 97 */     String condicion = "";
/*  76: 98 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$ProcesoAutoimpresorSRIEnum[procesoAutoimpresorSRI.ordinal()])
/*  77:    */     {
/*  78:    */     case 1: 
/*  79:100 */       fecha = " a.fechaCambioSoftware";
/*  80:101 */       break;
/*  81:    */     case 2: 
/*  82:103 */       fecha = " a.fechaDesde";
/*  83:104 */       condicion = " AND indicador_nuevo = true";
/*  84:105 */       break;
/*  85:    */     case 3: 
/*  86:107 */       fecha = " adpva.fechaInclusion";
/*  87:108 */       condicion = " AND indicador_nuevo = false AND adpva.fechaInclusion IS NOT NULL";
/*  88:109 */       break;
/*  89:    */     case 4: 
/*  90:111 */       fecha = " adpva.fechaExclusion";
/*  91:112 */       condicion = " AND adpva.fechaExclusion IS NOT NULL";
/*  92:113 */       break;
/*  93:    */     case 5: 
/*  94:115 */       fecha = " a.fechaBaja";
/*  95:    */     }
/*  96:119 */     sql.append(" SELECT DISTINCT " + fecha);
/*  97:120 */     sql.append(" FROM AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpva");
/*  98:121 */     sql.append(" JOIN adpva.autorizacionAutoimpresorSRI a ");
/*  99:122 */     sql.append(" WHERE a.idAutorizacionAutoimpresorSRI = :idAutorizacionAutoimpresorSRI " + condicion);
/* 100:    */     
/* 101:124 */     Query query = this.em.createQuery(sql.toString());
/* 102:    */     
/* 103:126 */     query.setParameter("idAutorizacionAutoimpresorSRI", Integer.valueOf(autorizacionAutoimpresorSRI.getIdAutorizacionAutoimpresorSRI()));
/* 104:    */     
/* 105:128 */     return query.getResultList();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public List<AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI> obtenerAutorizacionDocumentoPuntoDeVentaSRI(Date fecha, AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum procesoAutoimpresorSRI)
/* 109:    */   {
/* 110:134 */     StringBuilder sql = new StringBuilder();
/* 111:    */     
/* 112:136 */     boolean parametroFecha = false;
/* 113:137 */     String condicion = "adpva.fechaInclusion";
/* 114:139 */     switch (1.$SwitchMap$com$asinfo$as2$enumeraciones$ProcesoAutoimpresorSRIEnum[procesoAutoimpresorSRI.ordinal()])
/* 115:    */     {
/* 116:    */     case 2: 
/* 117:141 */       condicion = "adpva.fechaInclusion = :fecha";
/* 118:142 */       parametroFecha = true;
/* 119:143 */       break;
/* 120:    */     case 3: 
/* 121:146 */       condicion = "adpva.fechaInclusion = :fecha";
/* 122:147 */       parametroFecha = true;
/* 123:148 */       break;
/* 124:    */     case 4: 
/* 125:150 */       condicion = "adpva.fechaExclusion = :fecha";
/* 126:151 */       parametroFecha = true;
/* 127:152 */       break;
/* 128:    */     case 6: 
/* 129:154 */       condicion = "adpva.activo = true";
/* 130:155 */       break;
/* 131:    */     case 1: 
/* 132:157 */       condicion = "adpva.activo = true";
/* 133:158 */       break;
/* 134:    */     case 5: 
/* 135:160 */       condicion = "adpva.activo = true";
/* 136:    */     }
/* 137:164 */     sql.append(" SELECT adpva");
/* 138:165 */     sql.append(" FROM AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpva");
/* 139:166 */     sql.append(" JOIN FETCH adpva.autorizacionAutoimpresorSRI a");
/* 140:167 */     sql.append(" JOIN FETCH adpva.puntoDeVenta pv");
/* 141:168 */     sql.append(" WHERE " + condicion);
/* 142:169 */     sql.append(" and a.idAutorizacionAutoimpresorSRI = :idAutorizacionAutoimpresorSRI");
/* 143:    */     
/* 144:171 */     Query query = this.em.createQuery(sql.toString());
/* 145:172 */     if (parametroFecha) {
/* 146:173 */       query.setParameter("fecha", fecha);
/* 147:    */     }
/* 148:175 */     query.setParameter("idAutorizacionAutoimpresorSRI", Integer.valueOf(autorizacionAutoimpresorSRI.getIdAutorizacionAutoimpresorSRI()));
/* 149:    */     
/* 150:177 */     return query.getResultList();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<AutorizacionAutoimpresorSRI> obtenerAutorizacionVigente(int idOrganizacion, Date fecha, int idAutorizacionAutoimpresorSRIAnterior)
/* 154:    */   {
/* 155:184 */     StringBuilder sql = new StringBuilder();
/* 156:185 */     sql.append(" SELECT a ");
/* 157:186 */     sql.append(" FROM AutorizacionAutoimpresorSRI a ");
/* 158:187 */     sql.append(" WHERE :fecha between a.fechaDesde AND a.fechaHasta AND a.idOrganizacion = :idOrganizacion");
/* 159:188 */     sql.append(" AND a.idAutorizacionAutoimpresorSRI != :idAutorizacionAutoimpresorSRI");
/* 160:189 */     Query query = this.em.createQuery(sql.toString());
/* 161:    */     
/* 162:191 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 163:192 */     query.setParameter("fecha", fecha, TemporalType.DATE);
/* 164:193 */     query.setParameter("idAutorizacionAutoimpresorSRI", Integer.valueOf(idAutorizacionAutoimpresorSRIAnterior));
/* 165:    */     
/* 166:195 */     return query.getResultList();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public AutorizacionAutoimpresorSRI obtenerAutorizacionSRIVigente(int idOrganizacion)
/* 170:    */     throws ExcepcionAS2Financiero
/* 171:    */   {
/* 172:199 */     StringBuilder sql = new StringBuilder();
/* 173:200 */     sql.append(" SELECT a ");
/* 174:201 */     sql.append(" FROM AutorizacionAutoimpresorSRI a ");
/* 175:202 */     sql.append(" WHERE a.idOrganizacion = :idOrganizacion AND a.activo = 1");
/* 176:203 */     sql.append(" ORDER BY a.fechaHasta ");
/* 177:    */     
/* 178:205 */     Query query = this.em.createQuery(sql.toString());
/* 179:206 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 180:207 */     query.setMaxResults(1);
/* 181:    */     try
/* 182:    */     {
/* 183:209 */       return (AutorizacionAutoimpresorSRI)query.getSingleResult();
/* 184:    */     }
/* 185:    */     catch (NoResultException e)
/* 186:    */     {
/* 187:211 */       throw new ExcepcionAS2Financiero("msg_error_no_existe_autorizacion_autoimpresor_vigente");
/* 188:    */     }
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.sri.AutorizacionAutoimpresorSRIDao
 * JD-Core Version:    0.7.0.1
 */