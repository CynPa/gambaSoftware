/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Canal;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Zona;
/*  11:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  12:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  13:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  14:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  15:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  18:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  19:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  20:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.event.SelectEvent;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class EdicionFacturaClienteBean
/*  34:    */   extends PageController
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpresa servicioEmpresa;
/*  39:    */   @EJB
/*  40:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  41:    */   @EJB
/*  42:    */   private ServicioUsuario servicioUsuario;
/*  43:    */   @EJB
/*  44:    */   private ServicioZona servicioZona;
/*  45:    */   @EJB
/*  46:    */   private ServicioCanal servicioCanal;
/*  47:    */   private Empresa empresa;
/*  48:    */   private FacturaCliente facturaCliente;
/*  49:    */   private EntidadUsuario agenteComercial;
/*  50:    */   private Zona zona;
/*  51:    */   private Canal canal;
/*  52:    */   private List<EntidadUsuario> listaAgenteComercial;
/*  53:    */   private List<Zona> listaZona;
/*  54:    */   private List<Canal> listaCanal;
/*  55:    */   
/*  56:    */   public List<Empresa> autocompletarClientes(String consulta)
/*  57:    */   {
/*  58: 95 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<FacturaCliente> autocompletarFacturas(String consulta)
/*  62:    */   {
/*  63: 99 */     Map<String, String> filters = new HashMap();
/*  64:100 */     List<FacturaCliente> lista = new ArrayList();
/*  65:102 */     if (getEmpresa() != null)
/*  66:    */     {
/*  67:103 */       filters.put("empresa.idEmpresa", "" + getEmpresa().getId());
/*  68:104 */       if ((consulta != null) && (!consulta.isEmpty())) {
/*  69:105 */         filters.put("numero", "%" + consulta);
/*  70:    */       }
/*  71:107 */       filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/*  72:108 */       filters.put("estado", "!=" + Estado.ANULADO.toString());
/*  73:    */       
/*  74:110 */       lista = this.servicioFacturaCliente.obtenerListaCombo("fecha", true, filters);
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78:112 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/*  79:    */     }
/*  80:115 */     return lista;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String guardar()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:121 */       this.facturaCliente.setAgenteComercial(getAgenteComercial());
/*  88:122 */       this.facturaCliente.setZona(getZona());
/*  89:123 */       this.facturaCliente.setCanal(getCanal());
/*  90:124 */       this.servicioFacturaCliente.actualizarCabeceraFacturaCliente(this.facturaCliente);
/*  91:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  92:126 */       limpiar();
/*  93:    */     }
/*  94:    */     catch (ExcepcionAS2Financiero e)
/*  95:    */     {
/*  96:129 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  97:130 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/*  98:    */     }
/*  99:132 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   private void limpiar()
/* 103:    */   {
/* 104:136 */     setFacturaCliente(null);
/* 105:137 */     setEmpresa(null);
/* 106:138 */     setAgenteComercial(null);
/* 107:139 */     setZona(null);
/* 108:140 */     setCanal(null);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void actualizarFactura(SelectEvent event)
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:154 */       FacturaCliente facturaCliente = this.servicioFacturaCliente.cargarDetalle(((FacturaCliente)event.getObject()).getId());
/* 116:155 */       setAgenteComercial(facturaCliente.getAgenteComercial());
/* 117:156 */       setZona(facturaCliente.getZona());
/* 118:157 */       setCanal(facturaCliente.getCanal());
/* 119:    */     }
/* 120:    */     catch (ExcepcionAS2Ventas e)
/* 121:    */     {
/* 122:161 */       e.printStackTrace();
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public ServicioEmpresa getServicioEmpresa()
/* 127:    */   {
/* 128:176 */     return this.servicioEmpresa;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setServicioEmpresa(ServicioEmpresa servicioEmpresa)
/* 132:    */   {
/* 133:186 */     this.servicioEmpresa = servicioEmpresa;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Empresa getEmpresa()
/* 137:    */   {
/* 138:195 */     return this.empresa;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setEmpresa(Empresa empresa)
/* 142:    */   {
/* 143:205 */     this.empresa = empresa;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public FacturaCliente getFacturaCliente()
/* 147:    */   {
/* 148:214 */     return this.facturaCliente;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 152:    */   {
/* 153:224 */     this.facturaCliente = facturaCliente;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public EntidadUsuario getAgenteComercial()
/* 157:    */   {
/* 158:233 */     return this.agenteComercial;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 162:    */   {
/* 163:243 */     this.agenteComercial = agenteComercial;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Zona getZona()
/* 167:    */   {
/* 168:252 */     return this.zona;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setZona(Zona zona)
/* 172:    */   {
/* 173:262 */     this.zona = zona;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Canal getCanal()
/* 177:    */   {
/* 178:271 */     return this.canal;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setCanal(Canal canal)
/* 182:    */   {
/* 183:281 */     this.canal = canal;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<EntidadUsuario> getListaAgenteComercial()
/* 187:    */   {
/* 188:290 */     if (this.listaAgenteComercial == null) {
/* 189:291 */       this.listaAgenteComercial = this.servicioUsuario.getEntidadUsuario(AppUtil.getOrganizacion().getId(), true, AppUtil.getSucursal(), Boolean.valueOf(true));
/* 190:    */     }
/* 191:293 */     return this.listaAgenteComercial;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaAgenteComercial(List<EntidadUsuario> listaAgenteComercial)
/* 195:    */   {
/* 196:303 */     this.listaAgenteComercial = listaAgenteComercial;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public List<Zona> getListaZona()
/* 200:    */   {
/* 201:312 */     if (this.listaZona == null) {
/* 202:313 */       this.listaZona = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 203:    */     }
/* 204:315 */     return this.listaZona;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setListaZona(List<Zona> listaZona)
/* 208:    */   {
/* 209:325 */     this.listaZona = listaZona;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<Canal> getListaCanal()
/* 213:    */   {
/* 214:334 */     if (this.listaCanal == null) {
/* 215:335 */       this.listaCanal = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 216:    */     }
/* 217:337 */     return this.listaCanal;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setListaCanal(List<Canal> listaCanal)
/* 221:    */   {
/* 222:347 */     this.listaCanal = listaCanal;
/* 223:    */   }
/* 224:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.EdicionFacturaClienteBean
 * JD-Core Version:    0.7.0.1
 */