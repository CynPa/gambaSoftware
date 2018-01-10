/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.OneToMany;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.Max;
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="zona", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  21:    */ public class Zona
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="zona", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="zona")
/*  28:    */   @Column(name="id_zona")
/*  29:    */   private int idZona;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="codigo", nullable=false)
/*  35:    */   @Size(min=2, max=10)
/*  36:    */   private String codigo;
/*  37:    */   @Column(name="nombre", nullable=false, length=50)
/*  38:    */   @Size(min=2, max=50)
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="descripcion", nullable=false)
/*  41:    */   @Size(max=200)
/*  42:    */   private String descripcion;
/*  43:    */   @Column(name="porcentaje_descuento_maximo", nullable=false, precision=5, scale=2)
/*  44:    */   @Min(0L)
/*  45:    */   @Max(100L)
/*  46: 70 */   private BigDecimal porcentajeDescuentoMaximo = BigDecimal.ZERO;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @Column(name="predeterminado", nullable=false)
/*  50:    */   private boolean predeterminado;
/*  51:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="zona")
/*  52: 81 */   private List<Sector> listaSector = new ArrayList();
/*  53:    */   
/*  54:    */   public int getIdZona()
/*  55:    */   {
/*  56:113 */     return this.idZona;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdZona(int idZona)
/*  60:    */   {
/*  61:123 */     this.idZona = idZona;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdOrganizacion()
/*  65:    */   {
/*  66:132 */     return this.idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdOrganizacion(int idOrganizacion)
/*  70:    */   {
/*  71:142 */     this.idOrganizacion = idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdSucursal()
/*  75:    */   {
/*  76:151 */     return this.idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdSucursal(int idSucursal)
/*  80:    */   {
/*  81:161 */     this.idSucursal = idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getNombre()
/*  85:    */   {
/*  86:170 */     return this.nombre;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setNombre(String nombre)
/*  90:    */   {
/*  91:180 */     this.nombre = nombre;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getCodigo()
/*  95:    */   {
/*  96:189 */     return this.codigo;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setCodigo(String codigo)
/* 100:    */   {
/* 101:199 */     this.codigo = codigo;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getDescripcion()
/* 105:    */   {
/* 106:208 */     return this.descripcion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setDescripcion(String descripcion)
/* 110:    */   {
/* 111:218 */     this.descripcion = descripcion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isActivo()
/* 115:    */   {
/* 116:227 */     return this.activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setActivo(boolean activo)
/* 120:    */   {
/* 121:237 */     this.activo = activo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isPredeterminado()
/* 125:    */   {
/* 126:246 */     return this.predeterminado;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setPredeterminado(boolean predeterminado)
/* 130:    */   {
/* 131:256 */     this.predeterminado = predeterminado;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public BigDecimal getPorcentajeDescuentoMaximo()
/* 135:    */   {
/* 136:260 */     return this.porcentajeDescuentoMaximo;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setPorcentajeDescuentoMaximo(BigDecimal porcentajeDescuentoMaximo)
/* 140:    */   {
/* 141:264 */     this.porcentajeDescuentoMaximo = porcentajeDescuentoMaximo;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int getId()
/* 145:    */   {
/* 146:274 */     return this.idZona;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public List<Sector> getListaSector()
/* 150:    */   {
/* 151:278 */     return this.listaSector;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setListaSector(List<Sector> listaSector)
/* 155:    */   {
/* 156:282 */     this.listaSector = listaSector;
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Zona
 * JD-Core Version:    0.7.0.1
 */