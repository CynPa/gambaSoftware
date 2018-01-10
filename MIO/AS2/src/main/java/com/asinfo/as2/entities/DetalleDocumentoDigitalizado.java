/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_documento_digitalizado")
/*  22:    */ public class DetalleDocumentoDigitalizado
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable, Comparable<DetalleDocumentoDigitalizado>
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -5067816832914433772L;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="detalle_documento_digitalizado_empleado", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_documento_digitalizado_empleado")
/*  34:    */   @Column(name="id_detalle_documento_digitalizado", unique=true, nullable=false)
/*  35:    */   private int idDetalleDocumentoDigitalizado;
/*  36:    */   @Column(name="descripcion", nullable=true, length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="fecha_desde", nullable=true)
/*  40:    */   @Temporal(TemporalType.DATE)
/*  41:    */   private Date fechaDesde;
/*  42:    */   @Column(name="fecha_hasta", nullable=true)
/*  43:    */   @Temporal(TemporalType.DATE)
/*  44:    */   private Date fechaHasta;
/*  45:    */   @Column(name="fichero", nullable=true, length=200)
/*  46:    */   @Size(max=200)
/*  47:    */   private String fichero;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_documento_digitalizado_departamento", nullable=true)
/*  50:    */   private DocumentoDigitalizadoDepartamento documentoDigitalizadoDepartamento;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_documento_digitalizado_categoria_empresa", nullable=true)
/*  53:    */   private DocumentoDigitalizadoCategoriaEmpresa documentoDigitalizadoCategoriaEmpresa;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_empleado", nullable=true)
/*  56:    */   private Empleado empleado;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  59:    */   private Empresa empresa;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_documento_digitalizado", nullable=true)
/*  62:    */   private DocumentoDigitalizado documentoDigitalizado;
/*  63:    */   @Transient
/*  64: 86 */   private boolean primero = false;
/*  65:    */   
/*  66:    */   public int getIdOrganizacion()
/*  67:    */   {
/*  68: 90 */     return this.idOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public DocumentoDigitalizadoDepartamento getDocumentoDigitalizadoDepartamento()
/*  72:    */   {
/*  73: 94 */     return this.documentoDigitalizadoDepartamento;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setDocumentoDigitalizadoDepartamento(DocumentoDigitalizadoDepartamento documentoDigitalizadoDepartamento)
/*  77:    */   {
/*  78: 98 */     this.documentoDigitalizadoDepartamento = documentoDigitalizadoDepartamento;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Empleado getEmpleado()
/*  82:    */   {
/*  83:102 */     return this.empleado;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setEmpleado(Empleado empleado)
/*  87:    */   {
/*  88:106 */     this.empleado = empleado;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdOrganizacion(int idOrganizacion)
/*  92:    */   {
/*  93:110 */     this.idOrganizacion = idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdSucursal()
/*  97:    */   {
/*  98:114 */     return this.idSucursal;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdSucursal(int idSucursal)
/* 102:    */   {
/* 103:118 */     this.idSucursal = idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getIdDetalleDocumentoDigitalizado()
/* 107:    */   {
/* 108:122 */     return this.idDetalleDocumentoDigitalizado;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdDetalleDocumentoDigitalizado(int idDetalleDocumentoDigitalizadoEmpleado)
/* 112:    */   {
/* 113:126 */     this.idDetalleDocumentoDigitalizado = idDetalleDocumentoDigitalizadoEmpleado;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getDescripcion()
/* 117:    */   {
/* 118:130 */     return this.descripcion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setDescripcion(String descripcion)
/* 122:    */   {
/* 123:134 */     this.descripcion = descripcion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Date getFechaDesde()
/* 127:    */   {
/* 128:138 */     return this.fechaDesde;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setFechaDesde(Date fechaDesde)
/* 132:    */   {
/* 133:142 */     this.fechaDesde = fechaDesde;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Date getFechaHasta()
/* 137:    */   {
/* 138:146 */     return this.fechaHasta;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setFechaHasta(Date fechaHasta)
/* 142:    */   {
/* 143:150 */     this.fechaHasta = fechaHasta;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getFichero()
/* 147:    */   {
/* 148:154 */     return this.fichero;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setFichero(String fichero)
/* 152:    */   {
/* 153:158 */     this.fichero = fichero;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int getId()
/* 157:    */   {
/* 158:167 */     return this.idDetalleDocumentoDigitalizado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public boolean isPrimero()
/* 162:    */   {
/* 163:171 */     return this.primero;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setPrimero(boolean primero)
/* 167:    */   {
/* 168:175 */     this.primero = primero;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int compareTo(DetalleDocumentoDigitalizado o)
/* 172:    */   {
/* 173:181 */     return this.documentoDigitalizadoDepartamento.getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado().getNombre().compareTo(o.getDocumentoDigitalizadoDepartamento().getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado().getNombre());
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Empresa getEmpresa()
/* 177:    */   {
/* 178:186 */     return this.empresa;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setEmpresa(Empresa empresa)
/* 182:    */   {
/* 183:190 */     this.empresa = empresa;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public DocumentoDigitalizado getDocumentoDigitalizado()
/* 187:    */   {
/* 188:194 */     return this.documentoDigitalizado;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setDocumentoDigitalizado(DocumentoDigitalizado documentoDigitalizado)
/* 192:    */   {
/* 193:198 */     this.documentoDigitalizado = documentoDigitalizado;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public DocumentoDigitalizadoCategoriaEmpresa getDocumentoDigitalizadoCategoriaEmpresa()
/* 197:    */   {
/* 198:202 */     return this.documentoDigitalizadoCategoriaEmpresa;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setDocumentoDigitalizadoCategoriaEmpresa(DocumentoDigitalizadoCategoriaEmpresa documentoDigitalizadoCategoriaEmpresa)
/* 202:    */   {
/* 203:206 */     this.documentoDigitalizadoCategoriaEmpresa = documentoDigitalizadoCategoriaEmpresa;
/* 204:    */   }
/* 205:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleDocumentoDigitalizado
 * JD-Core Version:    0.7.0.1
 */