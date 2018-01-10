/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  11:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  15:    */ import java.io.PrintStream;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Calendar;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.List;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class EnvioCorreosMasivoBean
/*  28:    */   extends PageController
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1584134027790279238L;
/*  31:    */   @EJB
/*  32:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  33:    */   @EJB
/*  34:    */   private ServicioEmpresa servicioEmpresa;
/*  35:    */   @EJB
/*  36:    */   private ServicioUsuario servicioUsuario;
/*  37:    */   @EJB
/*  38:    */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  39:    */   private Empresa empresa;
/*  40:    */   private Date fechaDesde;
/*  41:    */   private Date fechaHasta;
/*  42:    */   private List<FacturaCliente> listaFacturaCliente;
/*  43: 62 */   private DocumentoBase documentoBase = DocumentoBase.FACTURA_CLIENTE;
/*  44:    */   private List<DocumentoBase> listaDocumentoBase;
/*  45:    */   
/*  46:    */   @PostConstruct
/*  47:    */   public void init()
/*  48:    */   {
/*  49: 68 */     Calendar calfechaDesde = Calendar.getInstance();
/*  50: 69 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  51: 70 */     this.fechaDesde = calfechaDesde.getTime();
/*  52: 71 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void enviarCorreosMasivo()
/*  56:    */   {
/*  57:    */     try
/*  58:    */     {
/*  59: 77 */       if ((this.documentoBase.equals(DocumentoBase.FACTURA_CLIENTE)) || (this.documentoBase.equals(DocumentoBase.NOTA_CREDITO_CLIENTE)) || 
/*  60: 78 */         (this.documentoBase.equals(DocumentoBase.NOTA_DEBITO_PROVEEDOR)))
/*  61:    */       {
/*  62: 80 */         List<FacturaCliente> listaFacturaCliente = new ArrayList();
/*  63: 81 */         listaFacturaCliente = this.servicioFacturaCliente.obtenerFacturasNotasCredito(AppUtil.getOrganizacion().getIdOrganizacion(), this.fechaDesde, this.fechaHasta, this.documentoBase, 
/*  64: 82 */           getEmpresa().getId());
/*  65: 83 */         for (FacturaCliente facturaCliente : listaFacturaCliente) {
/*  66: 84 */           this.servicioFacturaCliente.enviarMail(facturaCliente, facturaCliente.getEmail());
/*  67:    */         }
/*  68:    */       }
/*  69: 87 */       else if (this.documentoBase.equals(DocumentoBase.RETENCION_PROVEEDOR))
/*  70:    */       {
/*  71: 89 */         List<FacturaProveedorSRI> listaFacturaProveedorSRI = new ArrayList();
/*  72: 90 */         listaFacturaProveedorSRI = this.servicioFacturaProveedorSRI.obtenerRetencionesProveedor(AppUtil.getOrganizacion().getIdOrganizacion(), this.fechaDesde, this.fechaHasta, this.documentoBase, 
/*  73: 91 */           getEmpresa().getId());
/*  74: 93 */         for (FacturaProveedorSRI facturaProveedorSRI : listaFacturaProveedorSRI) {
/*  75: 94 */           this.servicioFacturaProveedorSRI.enviarMail(facturaProveedorSRI, facturaProveedorSRI.getEmail());
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79:    */     catch (Exception e)
/*  80:    */     {
/*  81:100 */       e.printStackTrace();
/*  82:    */       
/*  83:102 */       System.out.println("Error en generar algun reporte o enviar por mail al cliente" + e.getMessage());
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Date getFechaDesde()
/*  88:    */   {
/*  89:112 */     return this.fechaDesde;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setFechaDesde(Date fechaDesde)
/*  93:    */   {
/*  94:122 */     this.fechaDesde = fechaDesde;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Date getFechaHasta()
/*  98:    */   {
/*  99:131 */     return this.fechaHasta;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setFechaHasta(Date fechaHasta)
/* 103:    */   {
/* 104:141 */     this.fechaHasta = fechaHasta;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Empresa getEmpresa()
/* 108:    */   {
/* 109:150 */     if (this.empresa == null) {
/* 110:151 */       this.empresa = new Empresa();
/* 111:    */     }
/* 112:153 */     return this.empresa;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setEmpresa(Empresa empresa)
/* 116:    */   {
/* 117:163 */     this.empresa = empresa;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 121:    */   {
/* 122:167 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 126:    */   {
/* 127:171 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public List<FacturaCliente> getListaFacturaCliente()
/* 131:    */   {
/* 132:176 */     return this.listaFacturaCliente;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setListaFacturaCliente(List<FacturaCliente> listaFacturaCliente)
/* 136:    */   {
/* 137:180 */     this.listaFacturaCliente = listaFacturaCliente;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public DocumentoBase getDocumentoBase()
/* 141:    */   {
/* 142:184 */     return this.documentoBase;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setDocumentoBase(DocumentoBase documentoBase)
/* 146:    */   {
/* 147:188 */     this.documentoBase = documentoBase;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<DocumentoBase> getListaDocumentoBase()
/* 151:    */   {
/* 152:197 */     if (this.listaDocumentoBase == null)
/* 153:    */     {
/* 154:198 */       this.listaDocumentoBase = new ArrayList();
/* 155:199 */       this.listaDocumentoBase.add(DocumentoBase.NOTA_CREDITO_CLIENTE);
/* 156:200 */       this.listaDocumentoBase.add(DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 157:201 */       this.listaDocumentoBase.add(DocumentoBase.FACTURA_CLIENTE);
/* 158:202 */       this.listaDocumentoBase.add(DocumentoBase.RETENCION_PROVEEDOR);
/* 159:    */     }
/* 160:204 */     return this.listaDocumentoBase;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setListaDocumentoBase(List<DocumentoBase> listaDocumentoBase)
/* 164:    */   {
/* 165:214 */     this.listaDocumentoBase = listaDocumentoBase;
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.EnvioCorreosMasivoBean
 * JD-Core Version:    0.7.0.1
 */