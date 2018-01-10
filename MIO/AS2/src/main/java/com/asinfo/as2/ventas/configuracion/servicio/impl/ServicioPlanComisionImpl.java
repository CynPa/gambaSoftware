/*   1:    */ package com.asinfo.as2.ventas.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.PlanComisionDao;
/*   5:    */ import com.asinfo.as2.entities.DetalleVersionPlanComision;
/*   6:    */ import com.asinfo.as2.entities.DetalleVersionPlanComisionRangoDias;
/*   7:    */ import com.asinfo.as2.entities.DetalleVersionPlanComisionSupervisor;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.PlanComision;
/*  10:    */ import com.asinfo.as2.entities.RangoDiasComision;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.entities.VersionPlanComision;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioPlanComision;
/*  17:    */ import java.io.PrintStream;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.ejb.SessionContext;
/*  24:    */ import javax.ejb.Stateless;
/*  25:    */ import javax.ejb.TransactionAttribute;
/*  26:    */ import javax.ejb.TransactionAttributeType;
/*  27:    */ import javax.ejb.TransactionManagement;
/*  28:    */ import javax.ejb.TransactionManagementType;
/*  29:    */ 
/*  30:    */ @Stateless
/*  31:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  32:    */ public class ServicioPlanComisionImpl
/*  33:    */   extends AbstractServicioAS2
/*  34:    */   implements ServicioPlanComision
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private PlanComisionDao planComisionDao;
/*  39:    */   @EJB
/*  40:    */   private GenericoDao<VersionPlanComision> versionPlanComisionDao;
/*  41:    */   @EJB
/*  42:    */   private GenericoDao<DetalleVersionPlanComision> detalleVersionPlanComisionDao;
/*  43:    */   @EJB
/*  44:    */   private GenericoDao<DetalleVersionPlanComisionRangoDias> detalleVersionPlanComisionRangoDiasDao;
/*  45:    */   @EJB
/*  46:    */   private GenericoDao<DetalleVersionPlanComisionSupervisor> detalleVersionPlanComisionSupervisorDao;
/*  47:    */   
/*  48:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  49:    */   public void guardar(PlanComision planComision)
/*  50:    */     throws AS2Exception
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 66 */       this.planComisionDao.guardar(planComision);
/*  55: 67 */       for (VersionPlanComision version : planComision.getListaVersionPlanComision())
/*  56:    */       {
/*  57: 68 */         if (!version.isEliminado()) {
/*  58: 69 */           this.versionPlanComisionDao.guardar(version);
/*  59:    */         }
/*  60: 71 */         for (DetalleVersionPlanComision detalle : version.getListaDetalleVersionPlanComision())
/*  61:    */         {
/*  62: 72 */           if (version.isEliminado()) {
/*  63: 73 */             detalle.setEliminado(true);
/*  64:    */           }
/*  65: 75 */           if (!detalle.isEliminado()) {
/*  66: 76 */             this.detalleVersionPlanComisionDao.guardar(detalle);
/*  67:    */           }
/*  68: 78 */           for (DetalleVersionPlanComisionRangoDias detalleRangoDias : detalle.getListaDetalleVersionPlanComisionRangoDias())
/*  69:    */           {
/*  70: 79 */             detalleRangoDias.setEliminado((detalleRangoDias.getValor() == null) || 
/*  71: 80 */               (detalleRangoDias.getValor().compareTo(BigDecimal.ZERO) == 0));
/*  72: 81 */             if (detalle.isEliminado()) {
/*  73: 82 */               detalleRangoDias.setEliminado(true);
/*  74:    */             }
/*  75: 84 */             this.detalleVersionPlanComisionRangoDiasDao.guardar(detalleRangoDias);
/*  76:    */           }
/*  77: 86 */           if (detalle.isEliminado()) {
/*  78: 87 */             this.detalleVersionPlanComisionDao.guardar(detalle);
/*  79:    */           }
/*  80:    */         }
/*  81: 90 */         for (DetalleVersionPlanComisionSupervisor detalle : version.getListaDetalleVersionPlanComisionSupervisor())
/*  82:    */         {
/*  83: 91 */           if (version.isEliminado()) {
/*  84: 92 */             detalle.setEliminado(true);
/*  85:    */           }
/*  86: 94 */           this.detalleVersionPlanComisionSupervisorDao.guardar(detalle);
/*  87:    */         }
/*  88: 96 */         if (version.isEliminado()) {
/*  89: 97 */           this.versionPlanComisionDao.guardar(version);
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:101 */       e.printStackTrace();
/*  96:102 */       this.context.setRollbackOnly();
/*  97:103 */       throw new AS2Exception(e.getMessage());
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void eliminar(PlanComision planComision)
/* 102:    */   {
/* 103:109 */     planComision = cargarDetalle(planComision.getId());
/* 104:110 */     for (VersionPlanComision version : planComision.getListaVersionPlanComision())
/* 105:    */     {
/* 106:111 */       for (DetalleVersionPlanComision detalle : version.getListaDetalleVersionPlanComision())
/* 107:    */       {
/* 108:112 */         for (DetalleVersionPlanComisionRangoDias detalleRangoDias : detalle.getListaDetalleVersionPlanComisionRangoDias()) {
/* 109:113 */           this.detalleVersionPlanComisionRangoDiasDao.eliminar(detalleRangoDias);
/* 110:    */         }
/* 111:115 */         this.detalleVersionPlanComisionDao.eliminar(detalle);
/* 112:    */       }
/* 113:117 */       for (DetalleVersionPlanComisionSupervisor detalle : version.getListaDetalleVersionPlanComisionSupervisor()) {
/* 114:118 */         this.detalleVersionPlanComisionSupervisorDao.eliminar(detalle);
/* 115:    */       }
/* 116:120 */       this.versionPlanComisionDao.eliminar(version);
/* 117:    */     }
/* 118:122 */     this.planComisionDao.eliminar(planComision);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public PlanComision buscarPorId(int idPlanComision)
/* 122:    */   {
/* 123:127 */     return (PlanComision)this.planComisionDao.buscarPorId(Integer.valueOf(idPlanComision));
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<PlanComision> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 127:    */   {
/* 128:132 */     return this.planComisionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<PlanComision> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 132:    */   {
/* 133:137 */     return this.planComisionDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int contarPorCriterio(Map<String, String> filters)
/* 137:    */   {
/* 138:142 */     return this.planComisionDao.contarPorCriterio(filters);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public PlanComision cargarDetalle(int idPlanComision)
/* 142:    */   {
/* 143:147 */     return this.planComisionDao.cargarDetalle(idPlanComision);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List<VersionPlanComision> getReportePlanComision(PlanComision planComision)
/* 147:    */   {
/* 148:152 */     planComision = cargarDetalle(planComision.getId());
/* 149:153 */     List<VersionPlanComision> resultado = new ArrayList();
/* 150:154 */     for (VersionPlanComision version : planComision.getListaVersionPlanComision())
/* 151:    */     {
/* 152:156 */       for (DetalleVersionPlanComision detalle : version.getListaDetalleVersionPlanComision()) {
/* 153:157 */         for (DetalleVersionPlanComisionRangoDias detalleRangoDias : detalle.getListaDetalleVersionPlanComisionRangoDias()) {
/* 154:158 */           version.getListaDetalleVersionPlanComisionRangoDias().add(detalleRangoDias);
/* 155:    */         }
/* 156:    */       }
/* 157:161 */       resultado.add(version);
/* 158:    */     }
/* 159:163 */     return resultado;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void actualizarRangoDiasPorDetalleVersion(DetalleVersionPlanComision detalleVersionPlanComision, List<RangoDiasComision> listaRangoDiasComisions)
/* 163:    */   {
/* 164:169 */     for (RangoDiasComision rangoDias : listaRangoDiasComisions)
/* 165:    */     {
/* 166:170 */       boolean encontre = obtenerValorDetalleVersionPlanComision(detalleVersionPlanComision, rangoDias) != null;
/* 167:171 */       if (!encontre)
/* 168:    */       {
/* 169:172 */         DetalleVersionPlanComisionRangoDias detalleRango = new DetalleVersionPlanComisionRangoDias();
/* 170:173 */         detalleRango.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 171:174 */         detalleRango.setIdSucursal(AppUtil.getSucursal().getId());
/* 172:175 */         detalleRango.setDetalleVersionPlanComision(detalleVersionPlanComision);
/* 173:176 */         detalleRango.setRangoDiasCobro(rangoDias);
/* 174:177 */         detalleVersionPlanComision.getListaDetalleVersionPlanComisionRangoDias().add(detalleRango);
/* 175:    */       }
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   public DetalleVersionPlanComisionRangoDias obtenerValorDetalleVersionPlanComision(DetalleVersionPlanComision detalleVersionPlanComision, RangoDiasComision rangoDias)
/* 180:    */   {
/* 181:185 */     for (DetalleVersionPlanComisionRangoDias detalle : detalleVersionPlanComision.getListaDetalleVersionPlanComisionRangoDias())
/* 182:    */     {
/* 183:186 */       System.out.println("id: " + detalle.getRangoDiasCobro().getId());
/* 184:187 */       System.out.println("iddd: " + rangoDias.getId());
/* 185:188 */       if (detalle.getRangoDiasCobro().getId() == rangoDias.getId())
/* 186:    */       {
/* 187:189 */         System.out.println("retorne");
/* 188:190 */         return detalle;
/* 189:    */       }
/* 190:    */     }
/* 191:193 */     return null;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public PlanComision copiarPlanComision(PlanComision planComisionCopia)
/* 195:    */   {
/* 196:198 */     planComisionCopia.setIdPlanComision(0);
/* 197:199 */     planComisionCopia.setUsuarioCreacion(null);
/* 198:200 */     planComisionCopia.setFechaCreacion(null);
/* 199:201 */     planComisionCopia.setCodigo(planComisionCopia.getCodigo() + "_c");
/* 200:202 */     planComisionCopia.setNombre(planComisionCopia.getNombre() + "_c");
/* 201:203 */     for (VersionPlanComision version : planComisionCopia.getListaVersionPlanComision())
/* 202:    */     {
/* 203:204 */       version.setIdVersionPlanComision(0);
/* 204:205 */       version.setUsuarioCreacion(null);
/* 205:206 */       version.setFechaCreacion(null);
/* 206:207 */       version.setCodigo(version.getCodigo() + "_c");
/* 207:208 */       version.setNombre(version.getNombre() + "_c");
/* 208:209 */       for (DetalleVersionPlanComisionSupervisor detalle : version.getListaDetalleVersionPlanComisionSupervisor())
/* 209:    */       {
/* 210:210 */         detalle.setIdDetalleVersionPlanComisionSupervisor(0);
/* 211:211 */         detalle.setUsuarioCreacion(null);
/* 212:212 */         detalle.setFechaCreacion(null);
/* 213:    */       }
/* 214:214 */       for (DetalleVersionPlanComision detalleVersion : version.getListaDetalleVersionPlanComision())
/* 215:    */       {
/* 216:215 */         detalleVersion.setIdDetalleVersionPlanComision(0);
/* 217:216 */         detalleVersion.setUsuarioCreacion(null);
/* 218:217 */         detalleVersion.setFechaCreacion(null);
/* 219:218 */         for (DetalleVersionPlanComisionRangoDias detalleRangoDias : detalleVersion.getListaDetalleVersionPlanComisionRangoDias())
/* 220:    */         {
/* 221:219 */           detalleRangoDias.setIdDetalleVersionPlanComisionRangoDias(0);
/* 222:220 */           detalleRangoDias.setUsuarioCreacion(null);
/* 223:221 */           detalleRangoDias.setFechaCreacion(null);
/* 224:    */         }
/* 225:    */       }
/* 226:    */     }
/* 227:225 */     return planComisionCopia;
/* 228:    */   }
/* 229:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.impl.ServicioPlanComisionImpl
 * JD-Core Version:    0.7.0.1
 */