/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.entities.Caja;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.event.SelectEvent;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class PuntoDeVentaBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 5639709883850620117L;
/*  32:    */   @EJB
/*  33:    */   private ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  34:    */   @EJB
/*  35:    */   private ServicioSucursal servicioSucursal;
/*  36:    */   private PuntoDeVenta puntoDeVenta;
/*  37:    */   private LazyDataModel<PuntoDeVenta> listaPuntoDeVenta;
/*  38:    */   private List<Sucursal> listaSucursal;
/*  39:    */   private DataTable dtPuntoDeVenta;
/*  40:    */   private DataTable dtCaja;
/*  41:    */   
/*  42:    */   @PostConstruct
/*  43:    */   public void init()
/*  44:    */   {
/*  45: 72 */     this.listaPuntoDeVenta = new LazyDataModel()
/*  46:    */     {
/*  47:    */       private static final long serialVersionUID = 1L;
/*  48:    */       
/*  49:    */       public List<PuntoDeVenta> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  50:    */       {
/*  51: 78 */         List<PuntoDeVenta> lista = new ArrayList();
/*  52: 79 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  53:    */         try
/*  54:    */         {
/*  55: 81 */           lista = PuntoDeVentaBean.this.servicioPuntoDeVenta.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  56:    */         }
/*  57:    */         catch (Exception e)
/*  58:    */         {
/*  59: 84 */           e.printStackTrace();
/*  60:    */         }
/*  61: 86 */         PuntoDeVentaBean.this.listaPuntoDeVenta.setRowCount(PuntoDeVentaBean.this.servicioPuntoDeVenta.contarPorCriterio(filters));
/*  62: 87 */         return lista;
/*  63:    */       }
/*  64:    */     };
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String editar()
/*  68:    */   {
/*  69: 99 */     if (getPuntoDeVenta().getId() > 0)
/*  70:    */     {
/*  71:100 */       this.puntoDeVenta = this.servicioPuntoDeVenta.cargarDetalle(this.puntoDeVenta.getId());
/*  72:101 */       setEditado(true);
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76:103 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  77:    */     }
/*  78:106 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String guardar()
/*  82:    */   {
/*  83:    */     try
/*  84:    */     {
/*  85:117 */       for (Caja cj : getPuntoDeVenta().getListaCaja()) {
/*  86:118 */         cj.setIdSucursal(this.puntoDeVenta.getSucursal().getIdSucursal());
/*  87:    */       }
/*  88:120 */       this.servicioPuntoDeVenta.guardar(getPuntoDeVenta());
/*  89:    */       
/*  90:122 */       setEditado(false);
/*  91:123 */       limpiar();
/*  92:    */       
/*  93:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  94:    */     }
/*  95:    */     catch (ExcepcionAS2Financiero e)
/*  96:    */     {
/*  97:127 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  98:128 */       e.printStackTrace();
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 103:131 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 104:    */     }
/* 105:134 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String eliminar()
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:146 */       this.servicioPuntoDeVenta.eliminar(getPuntoDeVenta());
/* 113:    */       
/* 114:148 */       cargarDatos();
/* 115:    */       
/* 116:150 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 117:    */     }
/* 118:    */     catch (Exception e)
/* 119:    */     {
/* 120:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 121:153 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 122:    */     }
/* 123:155 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void onRowSelect(SelectEvent event)
/* 127:    */   {
/* 128:164 */     this.puntoDeVenta = ((PuntoDeVenta)event.getObject());
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String limpiar()
/* 132:    */   {
/* 133:174 */     this.puntoDeVenta = new PuntoDeVenta();
/* 134:    */     
/* 135:176 */     this.puntoDeVenta.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 136:177 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String cargarDatos()
/* 140:    */   {
/* 141:187 */     return "";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String agregarCaja()
/* 145:    */   {
/* 146:196 */     Caja c = new Caja();
/* 147:197 */     c.setPuntoDeVenta(this.puntoDeVenta);
/* 148:198 */     c.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 149:199 */     this.puntoDeVenta.getListaCaja().add(c);
/* 150:    */     
/* 151:201 */     return "";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String eliminarCaja()
/* 155:    */   {
/* 156:210 */     Caja c = (Caja)this.dtCaja.getRowData();
/* 157:211 */     c.setEliminado(true);
/* 158:212 */     return "";
/* 159:    */   }
/* 160:    */   
/* 161:    */   public List<Caja> getListaCaja()
/* 162:    */   {
/* 163:222 */     List<Caja> listaCaja = new ArrayList();
/* 164:223 */     for (Caja c : getPuntoDeVenta().getListaCaja()) {
/* 165:225 */       if (!c.isEliminado()) {
/* 166:226 */         listaCaja.add(c);
/* 167:    */       }
/* 168:    */     }
/* 169:230 */     return listaCaja;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public PuntoDeVenta getPuntoDeVenta()
/* 173:    */   {
/* 174:239 */     return this.puntoDeVenta;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta)
/* 178:    */   {
/* 179:249 */     this.puntoDeVenta = puntoDeVenta;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public LazyDataModel<PuntoDeVenta> getListaPuntoDeVenta()
/* 183:    */   {
/* 184:258 */     return this.listaPuntoDeVenta;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setListaPuntoDeVenta(LazyDataModel<PuntoDeVenta> listaPuntoDeVenta)
/* 188:    */   {
/* 189:268 */     this.listaPuntoDeVenta = listaPuntoDeVenta;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public DataTable getDtPuntoDeVenta()
/* 193:    */   {
/* 194:277 */     return this.dtPuntoDeVenta;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setDtPuntoDeVenta(DataTable dtPuntoDeVenta)
/* 198:    */   {
/* 199:287 */     this.dtPuntoDeVenta = dtPuntoDeVenta;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<Sucursal> getListaSucursal()
/* 203:    */   {
/* 204:296 */     if (this.listaSucursal == null) {
/* 205:297 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("codigo", true, null);
/* 206:    */     }
/* 207:299 */     return this.listaSucursal;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 211:    */   {
/* 212:309 */     this.listaSucursal = listaSucursal;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public DataTable getDtCaja()
/* 216:    */   {
/* 217:318 */     return this.dtCaja;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setDtCaja(DataTable dtCaja)
/* 221:    */   {
/* 222:328 */     this.dtCaja = dtCaja;
/* 223:    */   }
/* 224:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.PuntoDeVentaBean
 * JD-Core Version:    0.7.0.1
 */