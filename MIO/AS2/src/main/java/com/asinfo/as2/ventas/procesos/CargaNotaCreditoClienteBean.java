/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   6:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.JsfUtil;
/*  15:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  16:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  17:    */ import java.io.BufferedInputStream;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.io.InputStream;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.List;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.primefaces.component.datatable.DataTable;
/*  27:    */ import org.primefaces.event.FileUploadEvent;
/*  28:    */ import org.primefaces.model.LazyDataModel;
/*  29:    */ import org.primefaces.model.UploadedFile;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class CargaNotaCreditoClienteBean
/*  34:    */   extends PageControllerAS2
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  39:    */   @EJB
/*  40:    */   private ServicioNotaCreditoCliente servicioNotaCreditoCliente;
/*  41:    */   @EJB
/*  42:    */   private ServicioSecuencia servicioSecuencia;
/*  43:    */   private List<FacturaCliente> listaFacturaCliente;
/*  44:    */   private List<DetalleFacturaCliente> listaDetalleNotaCredito;
/*  45:    */   private DataTable dtFacturaCliente;
/*  46:    */   private FacturaCliente notaCredito;
/*  47:    */   private LazyDataModel<FacturaCliente> listaRecepcionProveedor;
/*  48:    */   
/*  49:    */   public String cargaNotaCreditoCliente(FileUploadEvent event)
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53: 55 */       String fileName = "migracion_factura_cliente" + event.getFile().getFileName();
/*  54:    */       
/*  55: 57 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  56: 58 */       PuntoDeVenta puntoVenta = AppUtil.getPuntoDeVenta();
/*  57:    */       
/*  58: 60 */       this.listaFacturaCliente = this.servicioFacturaCliente.cargaNotaCreditoCliente(AppUtil.getOrganizacion().getId(), fileName, input, 4, puntoVenta);
/*  59: 63 */       for (FacturaCliente nc : this.listaFacturaCliente)
/*  60:    */       {
/*  61: 64 */         nc = this.servicioNotaCreditoCliente.cargarSecuencia(nc, puntoVenta);
/*  62: 65 */         this.servicioSecuencia.actualizarSecuencia(nc.getDocumento().getSecuencia(), nc.getNumero());
/*  63: 66 */         this.servicioNotaCreditoCliente.guardar(nc);
/*  64:    */       }
/*  65: 71 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  66:    */     }
/*  67:    */     catch (AS2Exception e)
/*  68:    */     {
/*  69: 73 */       JsfUtil.addErrorMessage(e, "");
/*  70: 74 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  71: 75 */       e.printStackTrace();
/*  72:    */     }
/*  73:    */     catch (IOException e)
/*  74:    */     {
/*  75: 79 */       e.printStackTrace();
/*  76:    */     }
/*  77:    */     catch (ExcepcionAS2 e)
/*  78:    */     {
/*  79: 81 */       e.printStackTrace();
/*  80: 82 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  81: 83 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85: 85 */       e.printStackTrace();
/*  86: 86 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  87:    */     }
/*  88: 89 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void cargarDetalleNotaCredito()
/*  92:    */   {
/*  93: 93 */     this.listaDetalleNotaCredito = new ArrayList();
/*  94:    */     
/*  95: 95 */     FacturaCliente notaCredito = (FacturaCliente)this.dtFacturaCliente.getRowData();
/*  96: 96 */     for (DetalleFacturaCliente cp : notaCredito.getListaDetalleFacturaCliente()) {
/*  97: 97 */       this.listaDetalleNotaCredito.add(cp);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public List<FacturaCliente> getListaFacturaCliente()
/* 102:    */   {
/* 103:103 */     if (this.listaFacturaCliente == null) {
/* 104:104 */       this.listaFacturaCliente = new ArrayList();
/* 105:    */     }
/* 106:106 */     return this.listaFacturaCliente;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setListaFacturaCliente(List<FacturaCliente> listaFacturaCliente)
/* 110:    */   {
/* 111:110 */     this.listaFacturaCliente = listaFacturaCliente;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public List<DetalleFacturaCliente> getListaDetalleNotaCredito()
/* 115:    */   {
/* 116:114 */     return this.listaDetalleNotaCredito;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setListaDetalleNotaCredito(List<DetalleFacturaCliente> listaDetalleNotaCredito)
/* 120:    */   {
/* 121:118 */     this.listaDetalleNotaCredito = listaDetalleNotaCredito;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String editar()
/* 125:    */   {
/* 126:124 */     return null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String guardar()
/* 130:    */   {
/* 131:130 */     return null;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String eliminar()
/* 135:    */   {
/* 136:136 */     return null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String limpiar()
/* 140:    */   {
/* 141:142 */     return null;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String cargarDatos()
/* 145:    */   {
/* 146:148 */     return null;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public DataTable getDtFacturaCliente()
/* 150:    */   {
/* 151:152 */     return this.dtFacturaCliente;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setDtFacturaCliente(DataTable dtFacturaCliente)
/* 155:    */   {
/* 156:156 */     this.dtFacturaCliente = dtFacturaCliente;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public FacturaCliente getNotaCredito()
/* 160:    */   {
/* 161:160 */     return this.notaCredito;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setNotaCredito(FacturaCliente notaCredito)
/* 165:    */   {
/* 166:164 */     this.notaCredito = notaCredito;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public LazyDataModel<FacturaCliente> getListaRecepcionProveedor()
/* 170:    */   {
/* 171:168 */     return this.listaRecepcionProveedor;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setListaRecepcionProveedor(LazyDataModel<FacturaCliente> listaRecepcionProveedor)
/* 175:    */   {
/* 176:172 */     this.listaRecepcionProveedor = listaRecepcionProveedor;
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.CargaNotaCreditoClienteBean
 * JD-Core Version:    0.7.0.1
 */