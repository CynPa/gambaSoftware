/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.DecimalMin;
/*  15:    */ import javax.validation.constraints.Digits;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="detalle_guia_remision")
/*  21:    */ public class DetalleGuiaRemision
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="detalle_guia_remision", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_guia_remision")
/*  28:    */   @Column(name="id_detalle_guia_remision")
/*  29:    */   private int idDetalleGuiaRemision;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   @NotNull
/*  34:    */   private int idOrganizacion;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_producto", nullable=true)
/*  37:    */   private Producto producto;
/*  38:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  39:    */   @JoinColumn(name="id_guia_remision", nullable=true)
/*  40:    */   private GuiaRemision guiaRemision;
/*  41:    */   @Column(name="cantidad", nullable=true, precision=12, scale=4)
/*  42:    */   @Digits(integer=12, fraction=4)
/*  43:    */   @DecimalMin("0.0000")
/*  44: 47 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  45:    */   @Column(name="descripcion", nullable=true, length=400)
/*  46:    */   @Size(max=400)
/*  47: 52 */   private String descripcion = "";
/*  48:    */   @Column(name="nombre1", nullable=true, length=50)
/*  49:    */   @Size(max=50)
/*  50:    */   private String nombre1;
/*  51:    */   @Column(name="valor1", nullable=true, length=300)
/*  52:    */   @Size(max=300)
/*  53:    */   private String valor1;
/*  54:    */   @Column(name="nombre2", nullable=true, length=50)
/*  55:    */   @Size(max=50)
/*  56:    */   private String nombre2;
/*  57:    */   @Column(name="valor2", nullable=true, length=300)
/*  58:    */   @Size(max=300)
/*  59:    */   private String valor2;
/*  60:    */   @Column(name="nombre3", nullable=true, length=50)
/*  61:    */   @Size(max=50)
/*  62:    */   private String nombre3;
/*  63:    */   @Column(name="valor3", nullable=true, length=300)
/*  64:    */   @Size(max=300)
/*  65:    */   private String valor3;
/*  66:    */   
/*  67:    */   public int getIdSucursal()
/*  68:    */   {
/*  69: 87 */     return this.idSucursal;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdSucursal(int idSucursal)
/*  73:    */   {
/*  74: 91 */     this.idSucursal = idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdOrganizacion()
/*  78:    */   {
/*  79: 95 */     return this.idOrganizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdOrganizacion(int idOrganizacion)
/*  83:    */   {
/*  84: 99 */     this.idOrganizacion = idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getId()
/*  88:    */   {
/*  89:104 */     return this.idDetalleGuiaRemision;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdDetalleGuiaRemision()
/*  93:    */   {
/*  94:108 */     return this.idDetalleGuiaRemision;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdDetalleGuiaRemision(int idDetalleGuiaRemision)
/*  98:    */   {
/*  99:112 */     this.idDetalleGuiaRemision = idDetalleGuiaRemision;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Producto getProducto()
/* 103:    */   {
/* 104:116 */     return this.producto;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setProducto(Producto producto)
/* 108:    */   {
/* 109:120 */     this.producto = producto;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public BigDecimal getCantidad()
/* 113:    */   {
/* 114:124 */     return this.cantidad;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setCantidad(BigDecimal cantidad)
/* 118:    */   {
/* 119:128 */     this.cantidad = cantidad;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public GuiaRemision getGuiaRemision()
/* 123:    */   {
/* 124:132 */     return this.guiaRemision;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setGuiaRemision(GuiaRemision guiaRemision)
/* 128:    */   {
/* 129:136 */     this.guiaRemision = guiaRemision;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getNombre1()
/* 133:    */   {
/* 134:143 */     return this.nombre1;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setNombre1(String nombre1)
/* 138:    */   {
/* 139:150 */     this.nombre1 = nombre1;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getValor1()
/* 143:    */   {
/* 144:157 */     return this.valor1;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setValor1(String valor1)
/* 148:    */   {
/* 149:164 */     this.valor1 = valor1;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getNombre2()
/* 153:    */   {
/* 154:171 */     return this.nombre2;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setNombre2(String nombre2)
/* 158:    */   {
/* 159:178 */     this.nombre2 = nombre2;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getValor2()
/* 163:    */   {
/* 164:185 */     return this.valor2;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setValor2(String valor2)
/* 168:    */   {
/* 169:192 */     this.valor2 = valor2;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getNombre3()
/* 173:    */   {
/* 174:199 */     return this.nombre3;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setNombre3(String nombre3)
/* 178:    */   {
/* 179:206 */     this.nombre3 = nombre3;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String getValor3()
/* 183:    */   {
/* 184:213 */     return this.valor3;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setValor3(String valor3)
/* 188:    */   {
/* 189:220 */     this.valor3 = valor3;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String getDescripcion()
/* 193:    */   {
/* 194:227 */     return this.descripcion;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setDescripcion(String descripcion)
/* 198:    */   {
/* 199:234 */     this.descripcion = descripcion;
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleGuiaRemision
 * JD-Core Version:    0.7.0.1
 */