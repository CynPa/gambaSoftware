/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.datosbase.controller.DocumentoBean;
/*   8:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   9:    */ import com.asinfo.as2.entities.CuentaContable;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.MovimientoBancario;
/*  12:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.CuentaContableBean;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioMovimientoBancario;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ManagedProperty;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.primefaces.component.datatable.DataTable;
/*  27:    */ import org.primefaces.event.SelectEvent;
/*  28:    */ import org.primefaces.model.LazyDataModel;
/*  29:    */ import org.primefaces.model.SortOrder;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class MovimientoBancarioBean
/*  34:    */   extends PageControllerAS2
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = -3226904701594181051L;
/*  37:    */   private MovimientoBancario movimientoBancario;
/*  38:    */   private LazyDataModel<MovimientoBancario> listaMovimientoBancario;
/*  39:    */   private CuentaContable cuentaContable;
/*  40:    */   @EJB
/*  41:    */   ServicioMovimientoBancario servicioMovimientoBancario;
/*  42:    */   @EJB
/*  43:    */   ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  44:    */   @EJB
/*  45:    */   ServicioTipoAsiento servicioTipoAsiento;
/*  46:    */   @EJB
/*  47:    */   ServicioAsiento servicioAsiento;
/*  48:    */   @EJB
/*  49:    */   ServicioSecuencia servicioSecuencia;
/*  50:    */   @EJB
/*  51:    */   ServicioOrganizacion so;
/*  52:    */   @EJB
/*  53:    */   ServicioSucursal ss;
/*  54:    */   private DataTable dtMovimientoBancario;
/*  55:    */   @ManagedProperty("#{cuentaBancariaOrganizacion}")
/*  56:    */   private CuentaBancariaOrganizacionBean cuentaBancariaOrganizacionBean;
/*  57:    */   @ManagedProperty("#{documentoBean}")
/*  58:    */   private DocumentoBean documentoBean;
/*  59:    */   @ManagedProperty("#{cuentaContableBean}")
/*  60:    */   private CuentaContableBean cuentaContableBean;
/*  61:    */   
/*  62:    */   @PostConstruct
/*  63:    */   public void init()
/*  64:    */   {
/*  65: 74 */     this.listaMovimientoBancario = new LazyDataModel()
/*  66:    */     {
/*  67:    */       private static final long serialVersionUID = 1L;
/*  68:    */       
/*  69:    */       public List<MovimientoBancario> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  70:    */       {
/*  71: 80 */         List<MovimientoBancario> lista = new ArrayList();
/*  72: 81 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  73: 82 */         lista = MovimientoBancarioBean.this.servicioMovimientoBancario.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  74: 83 */         MovimientoBancarioBean.this.listaMovimientoBancario.setRowCount(MovimientoBancarioBean.this.servicioMovimientoBancario.contarPorCriterio(filters));
/*  75: 84 */         return lista;
/*  76:    */       }
/*  77:    */     };
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String editar()
/*  81:    */   {
/*  82: 91 */     if (this.movimientoBancario.getIdMovimientoBancario() > 0)
/*  83:    */     {
/*  84: 92 */       this.movimientoBancario = this.servicioMovimientoBancario.buscarPorId(Integer.valueOf(getMovimientoBancario().getId()));
/*  85: 93 */       setEditado(true);
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89: 95 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  90:    */     }
/*  91: 97 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String guardar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:104 */       this.servicioMovimientoBancario.guardar(this.movimientoBancario);
/*  99:105 */       cargarDatos();
/* 100:106 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 101:107 */       setEditado(false);
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 106:110 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 107:    */     }
/* 108:113 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String eliminar()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:119 */       this.servicioMovimientoBancario.eliminar(this.movimientoBancario);
/* 116:120 */       cargarDatos();
/* 117:121 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 118:    */     }
/* 119:    */     catch (Exception e)
/* 120:    */     {
/* 121:123 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 122:124 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 123:    */     }
/* 124:126 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String limpiar()
/* 128:    */   {
/* 129:131 */     this.movimientoBancario = new MovimientoBancario();
/* 130:132 */     this.movimientoBancario.setDocumento(new Documento());
/* 131:133 */     return "";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String cargarDatos()
/* 135:    */   {
/* 136:138 */     limpiar();
/* 137:    */     
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:144 */     return "";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void onRowSelect(SelectEvent event)
/* 146:    */   {
/* 147:148 */     this.movimientoBancario = ((MovimientoBancario)event.getObject());
/* 148:    */   }
/* 149:    */   
/* 150:    */   public MovimientoBancario getMovimientoBancario()
/* 151:    */   {
/* 152:152 */     if (this.movimientoBancario == null) {
/* 153:153 */       cargarDatos();
/* 154:    */     }
/* 155:155 */     return this.movimientoBancario;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void cargarCuentaContable()
/* 159:    */   {
/* 160:160 */     if ((getCuentaContable() != null) && 
/* 161:161 */       (!getCuentaContable().isIndicadorMovimiento()))
/* 162:    */     {
/* 163:166 */       this.cuentaContable.setTraNombreParaMostrar("");
/* 164:167 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setMovimientoBancario(MovimientoBancario movimientoBancario)
/* 169:    */   {
/* 170:174 */     this.movimientoBancario = movimientoBancario;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public LazyDataModel<MovimientoBancario> getListaMovimientoBancario()
/* 174:    */   {
/* 175:178 */     return this.listaMovimientoBancario;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setListaMovimientoBancario(LazyDataModel<MovimientoBancario> listaMovimientoBancario)
/* 179:    */   {
/* 180:182 */     this.listaMovimientoBancario = listaMovimientoBancario;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public DataTable getDtMovimientoBancario()
/* 184:    */   {
/* 185:186 */     return this.dtMovimientoBancario;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setDtMovimientoBancario(DataTable dtMovimientoBancario)
/* 189:    */   {
/* 190:190 */     this.dtMovimientoBancario = dtMovimientoBancario;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public CuentaBancariaOrganizacionBean getCuentaBancariaOrganizacionBean()
/* 194:    */   {
/* 195:194 */     return this.cuentaBancariaOrganizacionBean;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setCuentaBancariaOrganizacionBean(CuentaBancariaOrganizacionBean cuentaBancariaOrganizacionBean)
/* 199:    */   {
/* 200:198 */     this.cuentaBancariaOrganizacionBean = cuentaBancariaOrganizacionBean;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public DocumentoBean getDocumentoBean()
/* 204:    */   {
/* 205:202 */     return this.documentoBean;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setDocumentoBean(DocumentoBean documentoBean)
/* 209:    */   {
/* 210:206 */     this.documentoBean = documentoBean;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public CuentaContableBean getCuentaContableBean()
/* 214:    */   {
/* 215:210 */     return this.cuentaContableBean;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setCuentaContableBean(CuentaContableBean cuentaContableBean)
/* 219:    */   {
/* 220:214 */     this.cuentaContableBean = cuentaContableBean;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public CuentaContable getCuentaContable()
/* 224:    */   {
/* 225:218 */     return this.cuentaContable;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 229:    */   {
/* 230:222 */     this.cuentaContable = cuentaContable;
/* 231:    */   }
/* 232:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.MovimientoBancarioBean
 * JD-Core Version:    0.7.0.1
 */