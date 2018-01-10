/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.NotNull;
/*  11:    */ import javax.validation.constraints.Size;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="tipo_bodega", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  15:    */ public class TipoBodega
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="tipo_bodega", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_bodega")
/*  22:    */   @Column(name="id_tipo_bodega", unique=true, nullable=false)
/*  23:    */   private int idTipoBodega;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @Column(name="codigo", nullable=false, length=10)
/*  29:    */   @NotNull
/*  30:    */   @Size(min=2, max=10)
/*  31:    */   private String codigo;
/*  32:    */   @Column(name="nombre", nullable=false, length=50)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", length=200)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   private boolean activo;
/*  41:    */   @Column(name="predeterminado", nullable=false)
/*  42:    */   private boolean predeterminado;
/*  43:    */   
/*  44:    */   public TipoBodega() {}
/*  45:    */   
/*  46:    */   public TipoBodega(int idTipoBodega, String codigo, String nombre)
/*  47:    */   {
/*  48: 82 */     this.idTipoBodega = idTipoBodega;
/*  49: 83 */     this.codigo = codigo;
/*  50: 84 */     this.nombre = nombre;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 89 */     return this.idTipoBodega;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdTipoBodega()
/*  59:    */   {
/*  60: 98 */     return this.idTipoBodega;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdTipoBodega(int idTipoBodega)
/*  64:    */   {
/*  65:108 */     this.idTipoBodega = idTipoBodega;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70:117 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75:127 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdSucursal()
/*  79:    */   {
/*  80:136 */     return this.idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdSucursal(int idSucursal)
/*  84:    */   {
/*  85:146 */     this.idSucursal = idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getCodigo()
/*  89:    */   {
/*  90:155 */     return this.codigo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setCodigo(String codigo)
/*  94:    */   {
/*  95:165 */     this.codigo = codigo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getNombre()
/*  99:    */   {
/* 100:174 */     return this.nombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setNombre(String nombre)
/* 104:    */   {
/* 105:184 */     this.nombre = nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getDescripcion()
/* 109:    */   {
/* 110:193 */     return this.descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDescripcion(String descripcion)
/* 114:    */   {
/* 115:203 */     this.descripcion = descripcion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isActivo()
/* 119:    */   {
/* 120:212 */     return this.activo;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setActivo(boolean activo)
/* 124:    */   {
/* 125:222 */     this.activo = activo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isPredeterminado()
/* 129:    */   {
/* 130:231 */     return this.predeterminado;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setPredeterminado(boolean predeterminado)
/* 134:    */   {
/* 135:241 */     this.predeterminado = predeterminado;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String toString()
/* 139:    */   {
/* 140:246 */     return this.nombre;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoBodega
 * JD-Core Version:    0.7.0.1
 */