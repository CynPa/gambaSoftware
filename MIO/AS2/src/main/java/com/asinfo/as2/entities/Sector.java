/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.FetchType;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="sector", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo", "id_zona"})})
/*  17:    */ public class Sector
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="sector", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="sector")
/*  24:    */   @Column(name="id_sector")
/*  25:    */   private int idSector;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="codigo", nullable=false)
/*  31:    */   @Size(min=2, max=10)
/*  32:    */   private String codigo;
/*  33:    */   @Column(name="nombre", nullable=false, length=50)
/*  34:    */   @Size(min=2, max=50)
/*  35:    */   private String nombre;
/*  36:    */   @Column(name="descripcion", nullable=true)
/*  37:    */   @Size(max=200)
/*  38:    */   private String descripcion;
/*  39:    */   @Column(name="activo", nullable=false)
/*  40:    */   private boolean activo;
/*  41:    */   @Column(name="predeterminado", nullable=false)
/*  42:    */   private boolean predeterminado;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_zona", nullable=true)
/*  45:    */   private Zona zona;
/*  46:    */   
/*  47:    */   public int getIdSector()
/*  48:    */   {
/*  49: 86 */     return this.idSector;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setIdSector(int idSector)
/*  53:    */   {
/*  54: 96 */     this.idSector = idSector;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getIdOrganizacion()
/*  58:    */   {
/*  59:105 */     return this.idOrganizacion;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIdOrganizacion(int idOrganizacion)
/*  63:    */   {
/*  64:115 */     this.idOrganizacion = idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdSucursal()
/*  68:    */   {
/*  69:124 */     return this.idSucursal;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdSucursal(int idSucursal)
/*  73:    */   {
/*  74:134 */     this.idSucursal = idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getNombre()
/*  78:    */   {
/*  79:143 */     return this.nombre;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setNombre(String nombre)
/*  83:    */   {
/*  84:153 */     this.nombre = nombre;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getCodigo()
/*  88:    */   {
/*  89:162 */     return this.codigo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setCodigo(String codigo)
/*  93:    */   {
/*  94:172 */     this.codigo = codigo;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getDescripcion()
/*  98:    */   {
/*  99:181 */     return this.descripcion;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setDescripcion(String descripcion)
/* 103:    */   {
/* 104:191 */     this.descripcion = descripcion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean isActivo()
/* 108:    */   {
/* 109:200 */     return this.activo;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setActivo(boolean activo)
/* 113:    */   {
/* 114:210 */     this.activo = activo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isPredeterminado()
/* 118:    */   {
/* 119:219 */     return this.predeterminado;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setPredeterminado(boolean predeterminado)
/* 123:    */   {
/* 124:229 */     this.predeterminado = predeterminado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getId()
/* 128:    */   {
/* 129:234 */     return this.idSector;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Zona getZona()
/* 133:    */   {
/* 134:238 */     return this.zona;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setZona(Zona zona)
/* 138:    */   {
/* 139:242 */     this.zona = zona;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Sector
 * JD-Core Version:    0.7.0.1
 */