/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="documento_digitalizado", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  22:    */ public class DocumentoDigitalizado
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -1133788217704360695L;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="documento_digitalizado", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="documento_digitalizado")
/*  34:    */   @Column(name="id_documento_digitalizado", unique=true, nullable=false)
/*  35:    */   private int idDocumentoDigitalizado;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   @NotNull
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="caduca", nullable=false)
/*  41:    */   private boolean caduca;
/*  42:    */   @Column(name="descripcion", nullable=true, length=200)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="activo", nullable=false)
/*  46:    */   private boolean activo;
/*  47:    */   @Column(name="predeterminado", nullable=false)
/*  48:    */   private boolean predeterminado;
/*  49:    */   @Column(name="codigo", nullable=true, length=20)
/*  50:    */   @Size(max=20)
/*  51:    */   private String codigo;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_categoria_documento_digitalizado", nullable=false, insertable=true, updatable=true)
/*  54:    */   @NotNull
/*  55:    */   private CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado;
/*  56:    */   @Column(name="indicador_cliente", nullable=false)
/*  57:    */   private boolean indicadorCliente;
/*  58:    */   @Column(name="indicador_proveedor", nullable=false)
/*  59:    */   private boolean indicadorProveedor;
/*  60:    */   @Column(name="indicador_empleado", nullable=false)
/*  61:    */   private boolean indicadorEmpleado;
/*  62:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="documentoDigitalizado")
/*  63: 88 */   private List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizadoEmpleado = new ArrayList();
/*  64:    */   
/*  65:    */   public CategoriaDocumentoDigitalizado getCategoriaDocumentoDigitalizado()
/*  66:    */   {
/*  67: 92 */     return this.categoriaDocumentoDigitalizado;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setCategoriaDocumentoDigitalizado(CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado)
/*  71:    */   {
/*  72: 97 */     this.categoriaDocumentoDigitalizado = categoriaDocumentoDigitalizado;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getId()
/*  76:    */   {
/*  77:104 */     return this.idDocumentoDigitalizado;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdDocumentoDigitalizado()
/*  81:    */   {
/*  82:108 */     return this.idDocumentoDigitalizado;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdDocumentoDigitalizado(int idDocumentoDigitalizado)
/*  86:    */   {
/*  87:112 */     this.idDocumentoDigitalizado = idDocumentoDigitalizado;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getNombre()
/*  91:    */   {
/*  92:116 */     return this.nombre;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setNombre(String nombre)
/*  96:    */   {
/*  97:120 */     this.nombre = nombre;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getDescripcion()
/* 101:    */   {
/* 102:124 */     return this.descripcion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setDescripcion(String descripcion)
/* 106:    */   {
/* 107:128 */     this.descripcion = descripcion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isCaduca()
/* 111:    */   {
/* 112:132 */     return this.caduca;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setCaduca(boolean caduca)
/* 116:    */   {
/* 117:136 */     this.caduca = caduca;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getIdOrganizacion()
/* 121:    */   {
/* 122:140 */     return this.idOrganizacion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setIdOrganizacion(int idOrganizacion)
/* 126:    */   {
/* 127:144 */     this.idOrganizacion = idOrganizacion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getIdSucursal()
/* 131:    */   {
/* 132:148 */     return this.idSucursal;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setIdSucursal(int idSucursal)
/* 136:    */   {
/* 137:152 */     this.idSucursal = idSucursal;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isActivo()
/* 141:    */   {
/* 142:156 */     return this.activo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setActivo(boolean activo)
/* 146:    */   {
/* 147:160 */     this.activo = activo;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isPredeterminado()
/* 151:    */   {
/* 152:164 */     return this.predeterminado;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setPredeterminado(boolean predeterminado)
/* 156:    */   {
/* 157:168 */     this.predeterminado = predeterminado;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getCodigo()
/* 161:    */   {
/* 162:172 */     return this.codigo;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setCodigo(String codigo)
/* 166:    */   {
/* 167:176 */     this.codigo = codigo;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean isIndicadorCliente()
/* 171:    */   {
/* 172:180 */     return this.indicadorCliente;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setIndicadorCliente(boolean indicadorCliente)
/* 176:    */   {
/* 177:184 */     this.indicadorCliente = indicadorCliente;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isIndicadorProveedor()
/* 181:    */   {
/* 182:188 */     return this.indicadorProveedor;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setIndicadorProveedor(boolean indicadorProveedor)
/* 186:    */   {
/* 187:192 */     this.indicadorProveedor = indicadorProveedor;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public List<DetalleDocumentoDigitalizado> getListaDetalleDocumentoDigitalizadoEmpleado()
/* 191:    */   {
/* 192:196 */     return this.listaDetalleDocumentoDigitalizadoEmpleado;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setListaDetalleDocumentoDigitalizadoEmpleado(List<DetalleDocumentoDigitalizado> listaDetalleDocumentoDigitalizadoEmpleado)
/* 196:    */   {
/* 197:200 */     this.listaDetalleDocumentoDigitalizadoEmpleado = listaDetalleDocumentoDigitalizadoEmpleado;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isIndicadorEmpleado()
/* 201:    */   {
/* 202:204 */     return this.indicadorEmpleado;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setIndicadorEmpleado(boolean indicadorEmpleado)
/* 206:    */   {
/* 207:208 */     this.indicadorEmpleado = indicadorEmpleado;
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DocumentoDigitalizado
 * JD-Core Version:    0.7.0.1
 */