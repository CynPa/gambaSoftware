/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.TipoAsiento;
/*   8:    */ import com.asinfo.as2.entities.seguridad.LogAuditoria;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.enumeraciones.ProcesoAuditoriaEnum;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  13:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  14:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class MayorizarBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -4107074643652627012L;
/*  32:    */   @EJB
/*  33:    */   private ServicioAsiento servicioAsiento;
/*  34:    */   @EJB
/*  35:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  36:    */   @EJB
/*  37:    */   private ServicioGenerico<LogAuditoria> servicioLogAuditoria;
/*  38:    */   private Date fechaDesde;
/*  39:    */   private Date fechaHasta;
/*  40:    */   private Estado estado;
/*  41: 61 */   private TipoAsiento tipoAsiento = new TipoAsiento();
/*  42:    */   private List<TipoAsiento> listaTipoAsiento;
/*  43:    */   private Asiento asiento;
/*  44:    */   
/*  45:    */   public String editar()
/*  46:    */   {
/*  47: 72 */     return "";
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String guardar()
/*  51:    */   {
/*  52: 82 */     return "";
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void mayorizar()
/*  56:    */   {
/*  57: 86 */     this.estado = Estado.REVISADO;
/*  58: 87 */     procesar();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void desmayorizar()
/*  62:    */   {
/*  63: 91 */     this.estado = Estado.ELABORADO;
/*  64: 92 */     procesar();
/*  65:    */   }
/*  66:    */   
/*  67:    */   @PostConstruct
/*  68:    */   private void init()
/*  69:    */   {
/*  70: 97 */     this.fechaDesde = FuncionesUtiles.getFechaInicioMes(new Date());
/*  71: 98 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(new Date());
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setearHoy()
/*  75:    */   {
/*  76:102 */     this.fechaDesde = new Date();
/*  77:103 */     this.fechaHasta = new Date();
/*  78:    */   }
/*  79:    */   
/*  80:    */   private void procesar()
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:108 */       if (null != this.asiento)
/*  85:    */       {
/*  86:109 */         this.servicioAsiento.mayorizarDesmayorizarPorAsiento(this.estado, this.asiento);
/*  87:110 */         crearLogAuditoria(ProcesoAuditoriaEnum.MAYORIZAR_DESMAYORIZAR, "Proceso: Mayorizar/Desmayorizar Por Asiento------> Estado: " + this.estado + "--->Numero Asiento: " + this.asiento
/*  88:111 */           .getNumero() + "--->Concepto Asiento: " + this.asiento.getConcepto());
/*  89:    */       }
/*  90:    */       else
/*  91:    */       {
/*  92:114 */         this.servicioAsiento.mayorizarDesmayorizar(this.fechaDesde, this.fechaHasta, this.tipoAsiento, this.estado, AppUtil.getOrganizacion().getId());
/*  93:115 */         crearLogAuditoria(ProcesoAuditoriaEnum.MAYORIZAR_DESMAYORIZAR, "Proceso: Mayorizar/Desmayorizar------> Estado: " + this.estado + "--->Fecha desde:" + this.fechaDesde + "--->Fecha hasta:" + this.fechaHasta + (this.tipoAsiento != null ? "--->Tipo Asiento: " + this.tipoAsiento
/*  94:    */         
/*  95:117 */           .getNombre() : ""));
/*  96:    */       }
/*  97:119 */       if (this.estado.equals(Estado.REVISADO)) {
/*  98:120 */         addInfoMessage(getLanguageController().getMensaje("msg_info_mayorizar"));
/*  99:    */       } else {
/* 100:122 */         addInfoMessage(getLanguageController().getMensaje("msg_info_desmayorizar"));
/* 101:    */       }
/* 102:    */     }
/* 103:    */     catch (ExcepcionAS2Financiero e)
/* 104:    */     {
/* 105:125 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 106:126 */       LOG.error("ERROR AL PROCESAR", e);
/* 107:    */     }
/* 108:    */     catch (Exception e)
/* 109:    */     {
/* 110:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 111:129 */       LOG.error("ERROR AL PROCESAR", e);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String limpiar()
/* 116:    */   {
/* 117:140 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String cargarDatos()
/* 121:    */   {
/* 122:150 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Date getFechaDesde()
/* 126:    */   {
/* 127:154 */     return this.fechaDesde;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setFechaDesde(Date fechaDesde)
/* 131:    */   {
/* 132:158 */     this.fechaDesde = fechaDesde;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Date getFechaHasta()
/* 136:    */   {
/* 137:162 */     return this.fechaHasta;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setFechaHasta(Date fechaHasta)
/* 141:    */   {
/* 142:166 */     this.fechaHasta = fechaHasta;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String eliminar()
/* 146:    */   {
/* 147:177 */     return null;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Estado getEstado()
/* 151:    */   {
/* 152:181 */     return this.estado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setEstado(Estado estado)
/* 156:    */   {
/* 157:185 */     this.estado = estado;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public TipoAsiento getTipoAsiento()
/* 161:    */   {
/* 162:189 */     return this.tipoAsiento;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setTipoAsiento(TipoAsiento tipoAsiento)
/* 166:    */   {
/* 167:193 */     this.tipoAsiento = tipoAsiento;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public List<TipoAsiento> getListaTipoAsiento()
/* 171:    */   {
/* 172:202 */     if (this.listaTipoAsiento == null) {
/* 173:203 */       this.listaTipoAsiento = this.servicioTipoAsiento.obtenerListaCombo("nombre", true, null);
/* 174:    */     }
/* 175:205 */     return this.listaTipoAsiento;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setListaTipoAsiento(List<TipoAsiento> listaTipoAsiento)
/* 179:    */   {
/* 180:215 */     this.listaTipoAsiento = listaTipoAsiento;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List<Asiento> autocompletarAsiento(String consulta)
/* 184:    */   {
/* 185:219 */     HashMap<String, String> filters = new HashMap();
/* 186:220 */     if ((null != this.tipoAsiento) && (this.tipoAsiento.getId() != 0)) {
/* 187:221 */       filters.put("tipoAsiento.idTipoAsiento", String.valueOf(this.tipoAsiento.getId()));
/* 188:    */     }
/* 189:223 */     filters.put("estado", "!=" + Estado.ANULADO);
/* 190:224 */     filters.put("numero", "%" + consulta.trim() + "%");
/* 191:    */     
/* 192:226 */     return this.servicioAsiento.obtenerListaCombo("numero", true, filters);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Asiento getAsiento()
/* 196:    */   {
/* 197:233 */     return this.asiento;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setAsiento(Asiento asiento)
/* 201:    */   {
/* 202:241 */     this.asiento = asiento;
/* 203:    */   }
/* 204:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.MayorizarBean
 * JD-Core Version:    0.7.0.1
 */